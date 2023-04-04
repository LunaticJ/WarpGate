package io.github.lagom130.wrapGate.starGate.verticle;

import io.github.lagom130.wrapGate.starGate.route.handler.*;
import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.KeyStoreOptions;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.auth.authorization.AuthorizationProvider;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.auth.jwt.authorization.JWTAuthorization;
import io.vertx.ext.auth.jwt.impl.JWTAuthProviderImpl;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.TimeoutHandler;

import static io.github.lagom130.wrapGate.starGate.constant.BusAddress.CACHE_CLIENT_INFO_GET;

public class MainVerticle extends AbstractVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new MainVerticle());
  }

  @Override
  public void start(Promise<Void> startPromise) {
    ConfigRetriever retriever = ConfigRetriever.create(vertx);
    retriever.getConfig()
      .compose(config -> {
        JWTAuth jwtAuth = this.createJWTAuth(config);
        vertx.deployVerticle(new RedisVerticle(config.getString("redis")));
        vertx.deployVerticle(new CacheVerticle(config));
        Router router = this.initRouter(jwtAuth);
        this.addAuthRouter(router, jwtAuth);
        return vertx.createHttpServer().requestHandler(router).listen(config.getJsonObject("server").getInteger("port"));
      }).onSuccess(httpServer -> {
        LOGGER.info("HTTP server started on port " + httpServer.actualPort());
      })
      .onFailure(throwable -> LOGGER.error(throwable.getMessage(), throwable));
  }

  /**
   * gateway core router, necessary
   * @param jwtAuth
   * @return
   */
  private Router initRouter(JWTAuth jwtAuth) {
    Router router = Router.router(vertx);
    //log pre handler
    router.route().handler(LogPreHandler.create());
    // api route
    router.route("/apis/:apiId")
      .handler(TimeoutHandler.create(6000L, 504))
      .handler(BodyHandler.create())
      .handler(AuthHandler.create(vertx, jwtAuth))
      .handler(ApiMetaHandler.create(vertx))
      // if set maxWaitMS ,use blockHandler
      .handler(ApiGolbalLimitHandler.create(vertx))
      .blockingHandler(ExecuteHandler.create(vertx))
      .failureHandler(ApiFailureHandler.create());
    return router;
  }

  /**
   * TODO gateway system router, not necessary
   * @param router
   * @param config
   * @return
   */
  private void addCtrlRouter(Router router, JsonObject config) {

  }

  private void addAuthRouter(Router router, JWTAuth provider) {
    router.post("/auth/token").handler(BodyHandler.create())
      .handler(ctx -> {
        JsonObject requestBody = ctx.body().asJsonObject();
        String clientId = requestBody.getString("key", "");
        String clientSecret = requestBody.getString("secret","");
        vertx.eventBus().request(CACHE_CLIENT_INFO_GET, clientId).onSuccess(msg -> {
          JsonObject clientInfo = (JsonObject) msg.body();
          if (!clientSecret.equals(clientInfo.getString("secret"))) {
            ctx.end();
          }
          String token = provider.generateToken(
            new JsonObject().put("clientId", clientInfo.getString("id")),
            new JWTOptions().setAlgorithm("RS256").setExpiresInMinutes(10));
          JsonObject responseBody = new JsonObject();
          responseBody.put("accessToken", token);
          provider.authenticate(JsonObject.of("token", token)).onSuccess(user -> System.out.println(user)).onFailure(t -> t.printStackTrace());
          ctx.end(responseBody.toString());
        }).onFailure(Throwable::printStackTrace);
      });
  }

  private JWTAuth createJWTAuth(JsonObject config) {
    return JWTAuth.create(vertx, new JWTAuthOptions()
      .addPubSecKey(new PubSecKeyOptions()
        .setAlgorithm(config.getJsonObject("publicSecretKey").getString("algorithm"))
        .setBuffer(config.getJsonObject("publicSecretKey").getString("key")))
      .addPubSecKey(new PubSecKeyOptions()
        .setAlgorithm(config.getJsonObject("privateSecretKey").getString("algorithm"))
        .setBuffer(config.getJsonObject("privateSecretKey").getString("key"))
      ));
  }
}

package io.github.lagom130.wrapGate.starGate.verticle;

import io.github.lagom130.wrapGate.starGate.route.handler.*;
import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.TimeoutHandler;

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
        vertx.deployVerticle(new RedisVerticle(config.getString("redis")));
        vertx.deployVerticle(new CacheVerticle(config));
        Router router = this.initRouter(config);
        return vertx.createHttpServer().requestHandler(router).listen(config.getJsonObject("server").getInteger("port"));
      }).onSuccess(httpServer -> {
        LOGGER.info("HTTP server started on port " + httpServer.actualPort());
      })
      .onFailure(throwable -> LOGGER.error(throwable.getMessage(), throwable));
  }

  private Router initRouter(JsonObject config) {
    Router router = Router.router(vertx);
    //log pre handler
    router.route().handler(LogPreHandler.create());
    // api route
    router.route("/apis/:apiId")
      .handler(TimeoutHandler.create(6000L, 504))
      .handler(BodyHandler.create())
      .handler(ApiMetaHandler.create(vertx))
      // if set maxWaitMS ,use blockHandler
      .handler(ApiGolbalLimitHandler.create(vertx))
      .blockingHandler(ExecuteHandler.create(vertx))
      .failureHandler(ApiFailureHandler.create());
    return router;
  }
}

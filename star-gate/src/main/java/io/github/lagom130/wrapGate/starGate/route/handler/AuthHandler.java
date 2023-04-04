package io.github.lagom130.wrapGate.starGate.route.handler;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.authentication.TokenCredentials;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.RoutingContext;

/**
 * api meta handler
 */
public class AuthHandler implements Handler<RoutingContext> {
  private Vertx vertx;
  private JWTAuth jwtAuth;

  public AuthHandler(Vertx vertx, JWTAuth jwtAuth) {
    this.vertx = vertx;
    this.jwtAuth = jwtAuth;
  }

  public static AuthHandler create(Vertx vertx, JWTAuth jwtAuth) {
    return new AuthHandler(vertx, jwtAuth);
  }
  @Override
  public void handle(RoutingContext ctx) {
    String authorizationStr = ctx.request().getHeader("Authorization");
    String[] s = authorizationStr.split(" ");
    jwtAuth.authenticate(JsonObject.of("token", s[1])).onSuccess(user -> {
      System.out.println(user);
      ctx.next();
    }).onFailure(throwable -> {
      throwable.printStackTrace();
      ctx.fail(400);
    });
  }
}



package io.github.lagom130.wrapGate.starGate.route.handler;

import io.github.lagom130.wrapGate.starGate.exception.BizException;
import io.netty.util.internal.StringUtil;
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
    if (StringUtil.isNullOrEmpty(authorizationStr)) {
      ctx.fail(401);
    }
    String[] s = authorizationStr.split(" ");
    if (!"bearer".equalsIgnoreCase(s[0]) && s.length !=2) {
      ctx.fail(403, new BizException("bad authorization header"));
    }
    jwtAuth.authenticate(JsonObject.of("token", s[1])).onSuccess(user -> {
      ctx.put("clientId", user.get("clientId"));
      ctx.next();
    }).onFailure(throwable -> {
      throwable.printStackTrace();
      ctx.fail(403, throwable);
    });
  }
}



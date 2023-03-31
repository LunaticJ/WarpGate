package com.lunaticj.wrapGate.starGate.route.handler;

import com.lunaticj.wrapGate.starGate.verticle.HttpServerVerticle;
import io.vertx.core.Handler;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * biz log pre handler
 */
public class ApiFailureHandler implements Handler<RoutingContext> {
  private static final Logger LOGGER = LoggerFactory.getLogger(ApiFailureHandler.class);
  public static ApiFailureHandler create() {
    return new ApiFailureHandler();
  }
  @Override
  public void handle(RoutingContext ctx) {
    String gwErrorMsg = ctx.get("msg", "default msg");
    JsonObject bizLog = ctx.get("bizLog", new JsonObject());
    bizLog.put("gwRespStatusCode", ctx.statusCode());
    bizLog.put("gwErrorMsg", gwErrorMsg);
    bizLog.put("gwRespTimestamp", System.currentTimeMillis());
    LOGGER.error(bizLog.toString());
    ctx.response().setStatusCode(ctx.statusCode())
      .end(JsonObject.of("msg", gwErrorMsg).toString());
  }
}



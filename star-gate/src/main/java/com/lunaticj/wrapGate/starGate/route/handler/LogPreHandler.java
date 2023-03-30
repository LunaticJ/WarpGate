package com.lunaticj.wrapGate.starGate.route.handler;

import io.vertx.core.Handler;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * biz log pre handler
 */
public class LogPreHandler implements Handler<RoutingContext> {
  public static LogPreHandler create() {
    return new LogPreHandler();
  }
  @Override
  public void handle(RoutingContext ctx) {
    ctx.put("bizLog", JsonObject.of(
      "clientAddress", ctx.request().remoteAddress().hostAddress(),
      "totalRequestTimestamp", System.currentTimeMillis(),
      "requestUri", ctx.request().uri(),
      "requestMethod", ctx.request().method().name()));
    ctx.next();
  }
}



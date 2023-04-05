package io.github.lagom130.wrapGate.starGate.route.handler;

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
    int statusCode = ctx.statusCode();
    String errorMsg = switch (statusCode){
      case 401 -> "unauthorized";
      case 404 -> "not found api";
      case 403, 502 -> ctx.failure().getMessage();
      case 504 -> "api timeout";
      default -> {
        LOGGER.error(ctx.failure().getMessage(), ctx.failure());
        yield "gateway internal error";
      }
    };
    JsonObject bizLog = ctx.get("bizLog", new JsonObject());
    bizLog.put("gwRespStatusCode", statusCode);
    bizLog.put("gwErrorMsg", errorMsg);
    bizLog.put("gwRespTimestamp", System.currentTimeMillis());
    LOGGER.error(bizLog.toString());
    ctx.response().setStatusCode(statusCode)
      .end(JsonObject.of("msg", errorMsg).toString());
  }
}



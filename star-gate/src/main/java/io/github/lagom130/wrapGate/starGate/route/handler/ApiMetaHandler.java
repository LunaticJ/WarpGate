package io.github.lagom130.wrapGate.starGate.route.handler;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import static io.github.lagom130.wrapGate.starGate.constant.BusAddress.CACHE_API_INFO_GET;

/**
 * api meta handler
 */
public class ApiMetaHandler implements Handler<RoutingContext> {
  private Vertx vertx;

  public ApiMetaHandler(Vertx vertx) {
    this.vertx = vertx;
  }

  public static ApiMetaHandler create(Vertx vertx) {
    return new ApiMetaHandler(vertx);
  }
  @Override
  public void handle(RoutingContext ctx) {
    String apiId = ctx.pathParam("apiId");
    vertx.eventBus().request(CACHE_API_INFO_GET, apiId).onSuccess(msg -> {
      JsonObject apiInfo = (JsonObject) msg.body();
      if(apiInfo != null) {
        ctx.put("apiMeta", apiInfo).next();
      } else {
        ctx.put("msg", "not found api").fail(404);
      }
    }).onFailure(t -> ctx.fail(500, t));
  }
}



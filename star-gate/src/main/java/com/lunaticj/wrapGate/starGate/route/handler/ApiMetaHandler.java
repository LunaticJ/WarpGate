package com.lunaticj.wrapGate.starGate.route.handler;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.web.RoutingContext;

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
    SharedData sharedData = vertx.sharedData();
    sharedData.getAsyncMap("apis").compose(map -> map.get(apiId))
      .onSuccess(apiInfo -> {
        if(apiInfo != null) {
          ctx.put("apiMeta", apiInfo).next();
        } else {
          ctx.put("msg", "not found api");
          ctx.fail(404);
        }
      })
      .onFailure(t -> ctx.fail(400, t));
  }
}



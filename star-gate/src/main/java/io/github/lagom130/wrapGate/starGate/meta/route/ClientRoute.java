package io.github.lagom130.wrapGate.starGate.meta.route;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class ClientRoute {
  protected static void addRoute(Vertx vertx, Router router) {
    router.get("/meta/clients").handler(ctx -> {
      //todo:
      ctx.end();
    });
    router.get("/meta/clients/:id").handler(ctx -> {
      String id = ctx.pathParam("id");
      //todo:
      ctx.end();
    });
    router.get("/meta/clients/:id/apis").handler(ctx -> {
      String id = ctx.pathParam("id");
      //todo:
      ctx.end();
    });
    router.delete("/meta/clients/:id").handler(ctx -> {
      String id = ctx.pathParam("id");
      //todo:
      ctx.end();
    });
    router.delete("/meta/clients/:id/apis/:apiId").handler(ctx -> {
      String id = ctx.pathParam("id");
      String apiId = ctx.pathParam("apiId");
      //todo:
      ctx.end();
    });
    router.put("/meta/clients/:id").consumes("*/json").consumes("*/json").handler(BodyHandler.create()).handler(ctx -> {
      //todo:
      ctx.end();
    });
    router.put("/meta/clients/:id/apis/:apiId").consumes("*/json").handler(BodyHandler.create()).handler(ctx -> {
      //todo:
      ctx.end();
    });
    router.post("/meta/clients").consumes("*/json").handler(BodyHandler.create()).handler(ctx -> {
      //todo:
      ctx.end();
    });
  }
}

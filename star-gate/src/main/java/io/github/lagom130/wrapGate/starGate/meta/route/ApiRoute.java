package io.github.lagom130.wrapGate.starGate.meta.route;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class ApiRoute {
  protected static void addRoute(Vertx vertx, Router router) {
    router.get("/meta/apis").handler(ctx -> {
      //todo:
      ctx.end();
    });
    router.get("/meta/apis/:id").handler(ctx -> {
      String id = ctx.pathParam("id");
      //todo:
      ctx.end();
    });
    router.get("/meta/apis/:id/clients").handler(ctx -> {
      String id = ctx.pathParam("id");
      //todo:
      ctx.end();
    });
    router.delete("/meta/apis/:id").handler(ctx -> {
      String id = ctx.pathParam("id");
      //todo:
      ctx.end();
    });
    router.put("/meta/apis/:id").consumes("*/json").handler(BodyHandler.create()).handler(ctx -> {
      //todo:
      ctx.end();
    });
    router.post("/meta/apis").consumes("*/json").handler(BodyHandler.create()).handler(ctx -> {
      //todo:
      ctx.end();
    });
  }
}

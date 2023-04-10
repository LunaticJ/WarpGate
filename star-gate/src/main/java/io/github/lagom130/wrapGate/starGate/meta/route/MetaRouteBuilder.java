package io.github.lagom130.wrapGate.starGate.meta.route;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class MetaRouteBuilder {
  public static Router build(Vertx vertx, Router router, JsonObject config) {
    ApiRoute.addRoute(vertx, router);
    ClientRoute.addRoute(vertx, router);
    return router;
  }
}

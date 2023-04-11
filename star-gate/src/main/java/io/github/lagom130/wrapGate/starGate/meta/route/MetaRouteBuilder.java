package io.github.lagom130.wrapGate.starGate.meta.route;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class MetaRouteBuilder {
  public static Router build(Vertx vertx, Router router, JsonObject config) {
    new ApiRoute(vertx).addRoute(router);
    ClientRoute.addRoute(vertx, router);
    return router;
  }
}

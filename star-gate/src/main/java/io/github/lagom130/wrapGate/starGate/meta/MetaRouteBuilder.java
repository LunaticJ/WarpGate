package io.github.lagom130.wrapGate.starGate.meta;

import io.github.lagom130.wrapGate.starGate.meta.api.ApiRoute;
import io.github.lagom130.wrapGate.starGate.meta.client.ClientRoute;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.pgclient.PgPool;

public class MetaRouteBuilder {
  public static Router build(Vertx vertx, Router router, PgPool pool) {
    router.route("/meta/").handler(ctx -> {
      Vertx.vertx().getOrCreateContext().put("tenantId", ctx.request().getHeader("tenantId"));
    });
    // api
    new ApiRoute(pool).addRoute(router);
    // client
    new ClientRoute(pool).addRoute(router);
    return router;
  }
}

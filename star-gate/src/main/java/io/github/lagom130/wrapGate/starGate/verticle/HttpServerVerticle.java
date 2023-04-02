package io.github.lagom130.wrapGate.starGate.verticle;

import io.github.lagom130.wrapGate.starGate.route.handler.*;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.TimeoutHandler;

public class HttpServerVerticle extends AbstractVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(HttpServerVerticle.class);
  private int port;

  public HttpServerVerticle(int port) {
    this.port = port;
  }

  @Override
  public void start(Promise<Void> startPromise) {
    Router router = Router.router(vertx);
    //log pre handler
    router.route().handler(LogPreHandler.create());
    // api route
    router.route("/apis/:apiId")
      .handler(TimeoutHandler.create(6000L, 504))
      .handler(BodyHandler.create())
      .handler(ApiMetaHandler.create(vertx))
      .blockingHandler(ApiGolbalLimitHandler.create(vertx))
      .blockingHandler(ExecuteHandler.create(vertx))
      .failureHandler(ApiFailureHandler.create());
    vertx.createHttpServer().requestHandler(router).listen(port)
      .onSuccess(httpServer -> {
        System.out.println("HTTP server started on port " + httpServer.actualPort());
      })
      .onFailure(Throwable::printStackTrace);
  }
}

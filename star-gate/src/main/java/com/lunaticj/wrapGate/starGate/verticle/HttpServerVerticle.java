package com.lunaticj.wrapGate.starGate.verticle;

import com.lunaticj.wrapGate.starGate.route.handler.LogPreHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.Router;
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
      .handler(TimeoutHandler.create())
      .blockingHandler(ctx -> {
        String apiId = ctx.pathParam("apiId");
        vertx.sharedData().getAsyncMap("apis").compose(map -> map.get(apiId))
          .onSuccess(apiInfo -> System.out.println(apiInfo))
          .onFailure(Throwable::printStackTrace);
        ctx.end("hello!");
      },false);
    vertx.createHttpServer().requestHandler(router).listen(port)
      .onSuccess(httpServer -> {
        System.out.println("HTTP server started on port " + httpServer.actualPort());
      })
      .onFailure(Throwable::printStackTrace);
  }
}

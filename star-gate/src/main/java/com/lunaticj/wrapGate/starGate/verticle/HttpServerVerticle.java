package com.lunaticj.wrapGate.starGate.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class HttpServerVerticle extends AbstractVerticle {
  private int port;

  public HttpServerVerticle(int port) {
    this.port = port;
  }

  @Override
  public void start(Promise<Void> startPromise) {
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create()).handler(ctx -> ctx.end("hello!"));
    vertx.createHttpServer().requestHandler(router).listen(port)
      .onSuccess(httpServer -> {
        System.out.println("HTTP server started on port "+httpServer.actualPort());
      })
      .onFailure(Throwable::printStackTrace);
  }
}

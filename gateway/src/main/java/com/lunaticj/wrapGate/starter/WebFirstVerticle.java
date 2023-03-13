package com.lunaticj.wrapGate.starter;

import io.netty.util.internal.StringUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class WebFirstVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new WebFirstVerticle());
  }
  @Override
  public void start(Promise<Void> startPromise) {
    Router router = Router.router(vertx);
    router.route().handler(rc -> {
      System.out.println("123");
      String authorization = rc.request().headers().get("authorization");
      if (StringUtil.isNullOrEmpty(authorization) || !"admin".equalsIgnoreCase(authorization)) {
        rc.end("no permission");
      } else {
        rc.response().setChunked(true);
        rc.next();
      }
    });
    router.get().path("/books/:id").handler(routingContext -> {
      System.out.println(Thread.currentThread());
      routingContext.end(routingContext.pathParam("id"));
    });
    router.post().path("/books")
      .handler(BodyHandler.create())
      .blockingHandler(routingContext -> {
        System.out.println(Thread.currentThread());
        routingContext.end(routingContext.body().asJsonObject().getString("key"));
    });
    //don't block event loop thread!
    //don't block event loop thread!
    //don't block event loop thread!
    router.route().path("/blockE")
      .handler(BodyHandler.create())
      .handler(routingContext -> {
        try {
          Thread.sleep(10000);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        routingContext.end(Thread.currentThread().toString());
      });
    router.route().path("/blockW")
      .handler(BodyHandler.create())
      .blockingHandler(routingContext -> {
        try {
          Thread.sleep(10000);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        routingContext.end(Thread.currentThread().toString());
      });


    vertx.createHttpServer().requestHandler(router).listen(9527)
      .onSuccess(httpServer -> System.out.println("server start at port "+httpServer.actualPort()))
      .onFailure(Throwable::printStackTrace);
  }
}

package com.lunaticj.wrapGate.learn;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

import java.time.LocalDateTime;

public class WebSocketServerVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new WebSocketServerVerticle());
  }

  @Override
  public void start(Promise<Void> startPromise) {
    Router router = Router.router(vertx);
    router.route("/ws").handler(rc -> rc.request().toWebSocket()
      .onSuccess(serverWebSocket -> {
        serverWebSocket.textMessageHandler(System.out::println);
        serverWebSocket.writeTextMessage("hello web socket!");
        final long periodicId = vertx.setPeriodic(1000L, l -> serverWebSocket.writeTextMessage(LocalDateTime.now().toString()));
        serverWebSocket.closeHandler(v-> vertx.cancelTimer(periodicId));
        serverWebSocket.exceptionHandler(Throwable::printStackTrace);
      })
      .onFailure(Throwable::printStackTrace));



    vertx.createHttpServer().requestHandler(router).listen(9527)
      .onSuccess(httpServer -> {
        System.out.println("server start at port "+httpServer.actualPort());
        vertx.deployVerticle(new WebSocketClientVerticle());
      })
      .onFailure(Throwable::printStackTrace);
  }
}

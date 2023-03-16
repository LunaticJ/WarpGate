package com.lunaticj.wrapGate.learn;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class WebSocketClientVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new WebSocketClientVerticle());
  }

  @Override
  public void start(Promise<Void> startPromise) {
    vertx.createHttpClient().webSocket(9527,"127.0.0.1","/ws")
      .onSuccess(webSocket -> {
        webSocket.writeTextMessage("hello, I'm client");
        webSocket.textMessageHandler(s -> System.out.println(Thread.currentThread() +" - "+ s));
      })
      .onFailure(Throwable::printStackTrace);
  }
}

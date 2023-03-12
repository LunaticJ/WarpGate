package com.lunaticj.wrapGate.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;

public class EventBusTestVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new EventBusTestVerticle());
  }
  @Override
  public void start() throws Exception {
    EventBus eventBus = vertx.eventBus();
    MessageConsumer<String> consumer1 = eventBus.localConsumer("addr");
    MessageConsumer<String> consumer2 = eventBus.localConsumer("addr");
    consumer1.handler(it -> {
      System.out.println("con1 " + it.body());
      it.reply("reply!");
    });
    consumer2.handler(it -> {
      System.out.println("con2 " + it.body());
      it.reply("reply!");
    });
    eventBus.send("addr", "this is send");
    eventBus.publish("addr", "this is publish");
    eventBus.request("addr", "this is publish", messageAsyncResult -> {
        if (messageAsyncResult.succeeded()) {
          System.out.println(messageAsyncResult.result().body());
        }
      });
    vertx.close();
  }
}

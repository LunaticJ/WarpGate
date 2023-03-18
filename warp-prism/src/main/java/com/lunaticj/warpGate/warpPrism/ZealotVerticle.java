package com.lunaticj.warpGate.warpPrism;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ZealotVerticle extends AbstractVerticle {
  private String code;
  private int port;

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new ZealotVerticle("test", 4399));
  }

  public ZealotVerticle(String code, int port) {
    this.code = code;
    this.port = port;
  }

  public String getCode() {
    return code;
  }

  public int getPort() {
    return port;
  }


  @Override
  public void start(Promise<Void> startPromise) {
    Router router = Router.router(vertx);
    router.route("/"+code+"/*").handler(BodyHandler.create())
      .blockingHandler(rc -> {
        System.out.println(String.format("Zealot[%s]:%s", code, Thread.currentThread()));
        JsonObject params = new JsonObject();
        JsonObject headers = new JsonObject();
        MultiMap params1 = rc.request().params();
        params1.forEach((k, v) -> params.put(k, v));
        rc.request().headers().forEach((k, v) -> headers.put(k, v));
        String requestBody = rc.body().asString();
        JsonObject responseBody = new JsonObject();
        responseBody.put("path", rc.request().path());
        responseBody.put("method", rc.request().method().name());
        responseBody.put("uri", rc.request().uri());
        responseBody.put("absoluteURI", rc.request().absoluteURI());
        responseBody.put("params", rc.request().params().entries().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2)->v2)));
        responseBody.put("headers", rc.request().headers().entries().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2)->v2)));
        responseBody.put("requestBody", requestBody);
        rc.response().putHeader("zealot", code).end(responseBody.toString());

    });
    vertx.createHttpServer().requestHandler(router).listen(port)
      .onSuccess(httpServer -> System.out.println("HTTP server started on port "+httpServer.actualPort()))
      .onFailure(Throwable::printStackTrace);
  }

}


package io.github.lagom130.wrapGate.learn;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;

public class WebClientVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new WebClientVerticle());
  }
  @Override
  public void start(Promise<Void> startPromise) {
    WebClient webClient = WebClient.create(vertx);
    JsonObject body = new JsonObject();
    body.put("id", "123");
    webClient.postAbs("http://localhost:9527/books").sendJsonObject(body)
      .onSuccess(resp -> System.out.println(resp.body()))
      .onFailure(Throwable::printStackTrace);
  }
}

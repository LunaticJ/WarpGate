package com.lunaticj.warpGate.warpPrism;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.ext.web.handler.BodyHandler;

public class MainVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new MainVerticle());
    Vertx.vertx().deployVerticle(new ZealotVerticle("one", 9527));
//    Vertx.vertx().deployVerticle(new ZealotVerticle("two", 9528));
//    Vertx.vertx().deployVerticle(new ZealotVerticle("three", 9529));
  }

  @Override
  public void start(Promise<Void> startPromise) {
    Router router = Router.router(vertx);
    router.route("/zealot/:code/*").handler(BodyHandler.create())
        .blockingHandler(rc -> {
          String code = rc.pathParam("code");
          HttpServerResponse response = rc.response();
          response.setChunked(true);
          WebClient webClient = WebClient.create(vertx);
          HttpRequest<Buffer> bufferHttpRequest = webClient.requestAbs(rc.request().method(), "http://localhost:9527/"+code+"/" + rc.request().path());
          rc.request().headers().forEach((k,v) -> {
            bufferHttpRequest.putHeader(k,v);
          });
          bufferHttpRequest
            .putHeader("t","t")
            .as(BodyCodec.pipe(response))
            .sendBuffer(rc.body().buffer())
            .onSuccess(bufferHttpResponse ->{
              System.out.println(bufferHttpResponse.toString());
            })
            .onFailure(Throwable::printStackTrace);
        });
    vertx.createHttpServer().requestHandler(router).listen(4399)
      .onSuccess(httpServer -> System.out.println("HTTP server started on port "+httpServer.actualPort()))
      .onFailure(Throwable::printStackTrace);
  }
}

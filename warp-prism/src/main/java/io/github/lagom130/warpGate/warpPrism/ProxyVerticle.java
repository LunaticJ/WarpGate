package io.github.lagom130.warpGate.warpPrism;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.proxy.handler.ProxyHandler;
import io.vertx.httpproxy.HttpProxy;

public class ProxyVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new ProxyVerticle());
    Vertx.vertx().deployVerticle(new ZealotVerticle("one", 9527));
  }
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    HttpClient httpClient = vertx.createHttpClient();
    HttpProxy httpProxy = HttpProxy.reverseProxy(httpClient);
    httpProxy.origin(9527, "localhost");
    Router router = Router.router(vertx);
    router.route("/*")
      .handler(ProxyHandler.create(httpProxy));
    vertx.createHttpServer().requestHandler(router).listen(4399)
      .onSuccess(httpServer -> System.out.println("HTTP server started on port "+httpServer.actualPort()))
      .onFailure(Throwable::printStackTrace);
  }
}

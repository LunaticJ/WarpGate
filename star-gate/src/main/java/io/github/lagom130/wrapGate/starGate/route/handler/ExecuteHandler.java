package io.github.lagom130.wrapGate.starGate.route.handler;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;

/**
 * execute handler
 */
public class ExecuteHandler implements Handler<RoutingContext> {
  private Vertx vertx;

  public ExecuteHandler(Vertx vertx) {
    this.vertx = vertx;
  }

  public static ExecuteHandler create(Vertx vertx) {
    return new ExecuteHandler(vertx);
  }
  @Override
  public void handle(RoutingContext ctx) {
    String uri = ctx.request().uri();

    JsonObject apiMeta = ctx.get("apiMeta", new JsonObject());
    WebClient webClient = WebClient.create(vertx);
    String requestURI = uri.replace("/apis/"+apiMeta.getString("guid"), apiMeta.getString("path", ""));
    webClient.request(ctx.request().method(), apiMeta.getInteger("port"), apiMeta.getString("host"), requestURI)
      .putHeaders(ctx.request().headers())
        .sendBuffer(ctx.body().buffer()).onSuccess(resp -> {
        HttpServerResponse httpServerResponse = ctx.response().setStatusCode(resp.statusCode());
        resp.headers().forEach(httpServerResponse::putHeader);
        httpServerResponse.end(resp.bodyAsBuffer());
      }).onFailure(throwable -> {
        ctx.put("msg", throwable.getMessage());
        ctx.fail(502, throwable);
      });
  }
}



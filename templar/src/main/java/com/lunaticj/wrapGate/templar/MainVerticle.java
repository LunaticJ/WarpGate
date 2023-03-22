package com.lunaticj.wrapGate.templar;

import io.netty.util.internal.StringUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class MainVerticle extends AbstractVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) {
    Router router = Router.router(vertx);
    router.route().handler(ctx -> {
      LOGGER.info("Hello! "+ctx.request().remoteAddress());
      ctx.next();
    });
    router.route()
      .handler(BodyHandler.create())
      .blockingHandler(ctx -> {
        HttpServerRequest request = ctx.request();
        long block = 0;
        int statusCode = 200;
        JsonObject requestBody = JsonObject.of("code", 200, "msg", "quick api!");
        try {
          String blockStr = request.getParam("block", "");
          String codeStr = request.getParam("code", "200");
          String statusCodeStr = request.getParam("statusCode", "200");
          if (!StringUtil.isNullOrEmpty(blockStr) && (block = Long.parseLong(blockStr)) > 0L) {
            Thread.sleep(block);
          }
          if (!StringUtil.isNullOrEmpty(statusCodeStr)) {
            statusCode = Integer.parseInt(statusCodeStr);
          }
          if (!StringUtil.isNullOrEmpty(codeStr)) {
            requestBody.put("code", codeStr);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        ctx.response().setStatusCode(statusCode).end(requestBody.toString());
      });
    vertx.createHttpServer().requestHandler(router).listen(4399)
      .onSuccess(httpServer -> LOGGER.info("HTTP server started on port " + httpServer.actualPort()))
      .onFailure(throwable -> {
        LOGGER.error(throwable.getMessage(), throwable);
      });
  }
}

package com.lunaticj.wrapGate.templar;

import io.netty.util.internal.StringUtil;
import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.ResponseTimeHandler;
import io.vertx.ext.web.handler.TimeoutHandler;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class MainVerticle extends AbstractVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) {

    ConfigRetriever retriever = ConfigRetriever.create(vertx);
    retriever.getConfig()
      .compose(config -> {
        Router router = Router.router(vertx);
        this.sayHi(router);
        router.route()
          .handler(TimeoutHandler.create(30000))
          .handler(ResponseTimeHandler.create())
          .handler(BodyHandler.create())
          .blockingHandler(ctx -> {
            LOGGER.info("work on "+Thread.currentThread());
            HttpServerRequest request = ctx.request();
            JsonObject requestBody = config.getJsonObject("response");
            Optional.ofNullable(request.getParam("code")).ifPresent(code -> requestBody.put("code", code));
            this.block(request.getParam("block", ""));
            ctx.response().setStatusCode(Optional.ofNullable(request.getParam("statusCode"))
              .filter(statusCode -> !StringUtil.isNullOrEmpty(statusCode))
              .map(statusCode -> Integer.parseInt(statusCode))
              .orElse(200))
              .send(requestBody.toString());
            ctx.next();
          }, false);
        this.sayBye(router);
        return vertx.createHttpServer().requestHandler(router).listen(config.getInteger("server_port"));
      }).onSuccess(httpServer -> LOGGER.info("HTTP server started on port " + httpServer.actualPort()))
      .onFailure(throwable -> LOGGER.error(throwable.getMessage(), throwable));


  }

  private void sayHi(Router router) {
    router.route().handler(ctx -> {
      LOGGER.info("Hello! "+ctx.request().remoteAddress());
      LOGGER.info("uri: "+ctx.request().uri());
      ctx.put("startTimeStamp", System.currentTimeMillis());
      ctx.next();
    });
  }

  private void sayBye(Router router) {
    router.route().handler(ctx -> {
      Long startTimeStamp =ctx.get("startTimeStamp");
      long used = System.currentTimeMillis()-startTimeStamp;
      LOGGER.info("Bye! used "+used);
    });
  }

  private void block(String blockStr) {
    long block;
    if (!StringUtil.isNullOrEmpty(blockStr) && (block = Long.parseLong(blockStr)) > 0L) {
      try {
        Thread.sleep(block);
      } catch (InterruptedException e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
  }
}


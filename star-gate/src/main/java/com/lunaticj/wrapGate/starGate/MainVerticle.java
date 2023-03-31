package com.lunaticj.wrapGate.starGate;

import com.lunaticj.wrapGate.starGate.verticle.CacheVerticle;
import com.lunaticj.wrapGate.starGate.verticle.HttpServerVerticle;
import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonArray;

public class MainVerticle extends AbstractVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new MainVerticle());
  }

  @Override
  public void start(Promise<Void> startPromise) {
    ConfigRetriever retriever = ConfigRetriever.create(vertx);
    retriever.getConfig()
      .onSuccess(config -> {
        // deploy http server
        vertx.deployVerticle(new HttpServerVerticle(config.getJsonObject("server").getInteger("port")));
        vertx.deployVerticle(new CacheVerticle(config.getJsonArray("apis", new JsonArray()), config.getJsonArray("clients", new JsonArray())));
      })
      .onFailure(throwable -> LOGGER.error(throwable.getMessage(), throwable));
  }
}

package io.github.lagom130.wrapGate.starGate;

import io.github.lagom130.wrapGate.starGate.verticle.CacheVerticle;
import io.github.lagom130.wrapGate.starGate.verticle.HttpServerVerticle;
import io.github.lagom130.wrapGate.starGate.verticle.RedisVerticle;
import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

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
        vertx.deployVerticle(new RedisVerticle(config.getString("redis")));
        vertx.deployVerticle(new CacheVerticle(config));
      })
      .onFailure(throwable -> LOGGER.error(throwable.getMessage(), throwable));
  }
}

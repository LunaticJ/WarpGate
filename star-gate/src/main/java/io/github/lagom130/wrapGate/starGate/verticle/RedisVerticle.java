package io.github.lagom130.wrapGate.starGate.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;

import java.util.List;

import static io.github.lagom130.wrapGate.starGate.constant.BusAddress.REDIS_EVAL;

public class RedisVerticle extends AbstractVerticle {
  private String redisUrl;
  private RedisAPI redisAPI;

  public RedisVerticle(String redisUrl) {
    this.redisUrl = redisUrl;
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Redis.createClient(vertx, redisUrl).connect().onSuccess(conn -> {
      this.redisAPI = RedisAPI.api(conn);
      vertx.eventBus().consumer(REDIS_EVAL, this::eval);
    }).onFailure(Throwable::printStackTrace);

  }

  private void eval(Message<List<String>> msg) {
    List<String> args = msg.body();
    redisAPI.eval(args).onSuccess(response -> {
      msg.reply(response.toInteger());
    }).onFailure(Throwable::printStackTrace);
  }
}

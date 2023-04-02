package io.github.lagom130.wrapGate.learn;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.Response;

import java.util.List;

public class RedisVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new RedisVerticle());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Redis.createClient(vertx, "redis://default:redispw@localhost:32769/0").connect()
      .onSuccess(redisConnection -> {
        RedisAPI api = RedisAPI.api(redisConnection);
        api.incr("test").onSuccess(response -> System.out.println(response.toInteger()))
          .onFailure(Throwable::printStackTrace);
        api.expireat(List.of("test", "1000")).onSuccess(System.out::println);
      });

  }
}

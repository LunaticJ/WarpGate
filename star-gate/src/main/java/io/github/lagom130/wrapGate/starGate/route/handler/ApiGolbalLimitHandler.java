package io.github.lagom130.wrapGate.starGate.route.handler;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

import static io.github.lagom130.wrapGate.starGate.constant.BusAddress.*;
import static io.github.lagom130.wrapGate.starGate.util.RedisUtils.TOKEN_BUCKET_LUA_SCRIPT;

/**
 * api meta handler
 */
public class ApiGolbalLimitHandler implements Handler<RoutingContext> {
  private Vertx vertx;

  private static final String TOKEN_BUCKET_API_GLOBAL_KEY_PREFIX = "token-bucket:api:global:";

  public ApiGolbalLimitHandler(Vertx vertx) {
    this.vertx = vertx;
  }

  public static ApiGolbalLimitHandler create(Vertx vertx) {
    return new ApiGolbalLimitHandler(vertx);
  }
  @Override
  public void handle(RoutingContext ctx) {
    String apiId = ctx.pathParam("apiId");
    EventBus eventBus = vertx.eventBus();
    eventBus.request(CACHE_API_GLOBAL_LIMIT_GET, apiId).onSuccess(msg -> {
      JsonObject apiLimit = (JsonObject) msg.body();
      if(apiLimit == null) {
        ctx.next();
        return;
      }
      List<String> args = List.of(TOKEN_BUCKET_LUA_SCRIPT,
        "1",
        TOKEN_BUCKET_API_GLOBAL_KEY_PREFIX + apiId,
        "1",
        apiLimit.getInteger("maxWaitMS") * 1000L + "",
        apiLimit.getString("maxTokens"),
        apiLimit.getString("secTokens"));
        eventBus.request(REDIS_EVAL, args).onSuccess(redisMsg -> {
          Integer resp = (Integer) redisMsg.body();
          if (resp == -1) {
            ctx.put("msg", "this api have exceeded the global rate limit");
            ctx.fail(503);
          } else if(resp > 0) {
            vertx.setTimer(resp/1000, l -> ctx.next());
          } else {
            ctx.next();
          }
        }).onFailure(t -> {
          t.printStackTrace();
          ctx.fail(500, t);
        });
    }).onFailure(t -> {
      t.printStackTrace();
      ctx.fail(500, t);
    });
  }
}



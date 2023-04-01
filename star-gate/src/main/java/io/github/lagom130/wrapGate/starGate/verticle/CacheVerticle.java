package io.github.lagom130.wrapGate.starGate.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

import static io.github.lagom130.wrapGate.starGate.constant.BusAddress.CACHE_API_INFO_GET;
import static io.github.lagom130.wrapGate.starGate.constant.BusAddress.CACHE_CLIENT_INFO_GET;

public class CacheVerticle extends AbstractVerticle {
  private Map<String, JsonObject> apisMap;
  private Map<String, JsonObject> clientsMap;

  public CacheVerticle(JsonArray apis, JsonArray clients) {
    Map<String, JsonObject> apisMap = new HashMap<>();
    Map<String, JsonObject> clientsMap = new HashMap<>();
    for (int i = 0; i < apis.size(); i++) {
      JsonObject api = apis.getJsonObject(i);
      apisMap.put(api.getString("guid"), api);
    }
    for (int i = 0; i < clients.size(); i++) {
      JsonObject client = clients.getJsonObject(i);
      clientsMap.put(client.getString("guid"), client);
    }
    this.apisMap = apisMap;
    this.clientsMap = clientsMap;
  }

  @Override
  public void start(Promise<Void> startPromise) {
    EventBus eventBus = vertx.eventBus();
    eventBus.consumer(CACHE_API_INFO_GET, msg -> {
      JsonObject jsonObject = apisMap.get(msg.body());
      msg.reply(jsonObject);
    });
    eventBus.consumer(CACHE_CLIENT_INFO_GET, msg -> {
      JsonObject jsonObject = clientsMap.get(msg.body());
      msg.reply(jsonObject);
    });
  }
}

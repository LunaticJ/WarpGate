package io.github.lagom130.wrapGate.starGate.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

import static io.github.lagom130.wrapGate.starGate.constant.BusAddress.*;

public class CacheVerticle extends AbstractVerticle {
  private Map<String, JsonObject> apisMap;
  private Map<String, JsonObject> clientsMap;
  private Map<String, JsonObject> apiGlobalLimitsMap;

  public CacheVerticle(JsonObject config) {
    JsonArray apis = config.getJsonArray("apis", new JsonArray());
    JsonArray clients = config.getJsonArray("clients", new JsonArray());
    JsonArray apiGlobalLimits = config.getJsonArray("apiGlobalLimits", new JsonArray());
    Map<String, JsonObject> apisMap = new HashMap<>();
    Map<String, JsonObject> clientsMap = new HashMap<>();
    Map<String, JsonObject> apiGlobalLimitsMap = new HashMap<>();
    for (int i = 0; i < apis.size(); i++) {
      JsonObject api = apis.getJsonObject(i);
      apisMap.put(api.getString("id"), api);
    }
    for (int i = 0; i < clients.size(); i++) {
      JsonObject client = clients.getJsonObject(i);
      clientsMap.put(client.getString("id"), client);
    }
    for (int i = 0; i < apiGlobalLimits.size(); i++) {
      JsonObject item = apiGlobalLimits.getJsonObject(i);
      apiGlobalLimitsMap.put(item.getString("apiId"), item);
    }
    this.apisMap = apisMap;
    this.clientsMap = clientsMap;
    this.apiGlobalLimitsMap = apiGlobalLimitsMap;
  }

  @Override
  public void start(Promise<Void> startPromise) {
    EventBus eventBus = vertx.eventBus();
    eventBus.consumer(CACHE_API_INFO_GET, this::getApiInfo);
    eventBus.consumer(CACHE_CLIENT_INFO_GET,this::getClientInfo);
    eventBus.consumer(CACHE_API_GLOBAL_LIMIT_GET,this::getApiGlobalLimit);
  }

  private void getApiInfo(Message<String> msg) {
    msg.reply(apisMap.get(msg.body()));
  }

  private void getClientInfo(Message<String> msg) {
    msg.reply(clientsMap.get(msg.body()));
  }


  private void getApiGlobalLimit(Message<String> msg) {
    msg.reply(apiGlobalLimitsMap.get(msg.body()));
  }
}

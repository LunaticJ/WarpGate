package com.lunaticj.wrapGate.starGate.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.AsyncMap;
import io.vertx.core.shareddata.SharedData;

public class CacheVerticle extends AbstractVerticle {
  private JsonArray apis;
  private JsonArray apps;

  public CacheVerticle(JsonArray apis, JsonArray apps) {
    this.apis = apis;
    this.apps = apps;
  }

  @Override
  public void start(Promise<Void> startPromise) {
    SharedData sharedData = vertx.sharedData();
    Future<AsyncMap<Object, Object>> getApisMapFuture = sharedData.getAsyncMap("apis");
    Future<Void> putApiFuture = null;
    for (int i = 0; i < apis.size(); i++) {
      JsonObject api = apis.getJsonObject(i);
      putApiFuture = getApisMapFuture.compose(apisMap -> apisMap.put(api.getString("guid"), api));
    }
    putApiFuture.onSuccess(v -> System.out.println("api cache load complete"))
      .onFailure(Throwable::printStackTrace);
    Future<AsyncMap<Object, Object>> getAppsMapFuture = sharedData.getAsyncMap("apps");
    Future<Void> putAppFuture = null;
    for (int i = 0; i < apps.size(); i++) {
      JsonObject app = apps.getJsonObject(i);
      putAppFuture = getAppsMapFuture.compose(apisMap -> apisMap.put(app.getString("guid"), app));
    }
    putAppFuture.onSuccess(v -> System.out.println("app cache load complete"))
      .onFailure(Throwable::printStackTrace);
  }
}

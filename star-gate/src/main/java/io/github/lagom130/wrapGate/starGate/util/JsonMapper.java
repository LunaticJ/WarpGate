package io.github.lagom130.wrapGate.starGate.util;

import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Row;

public class JsonMapper {

  public static <T> T mapper(Row row, Class<T> tClass) {
    JsonObject json = new JsonObject();
    for (int i = 0; i < row.size(); ++i) {
      json.getMap().put(row.getColumnName(i), row.getValue(i));
    }
    return json.mapTo(tClass);
  }
}

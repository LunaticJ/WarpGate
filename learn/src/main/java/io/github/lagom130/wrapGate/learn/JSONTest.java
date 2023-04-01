package io.github.lagom130.wrapGate.learn;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.Json;

public class JSONTest extends AbstractVerticle {
  public static void main(String[] args) {
    String str = "{\"first_name\":\"aBc\"}";
    TestBean testBean = Json.decodeValue(str, TestBean.class);
    System.out.println(testBean);
  }
  @Override
  public void start(Promise<Void> startPromise) throws Exception {

  }
}

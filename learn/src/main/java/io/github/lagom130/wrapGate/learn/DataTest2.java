package io.github.lagom130.wrapGate.learn;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.templates.SqlTemplate;

import java.time.OffsetDateTime;
import java.util.*;

public class DataTest2 extends AbstractVerticle {
  private PgPool pool;

  public DataTest2(PgPool pool) {
    this.pool = pool;
  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    PgConnectOptions  pgConnectOptions = new PgConnectOptions();
    pgConnectOptions
      .setPort(32769)
      .setHost("localhost")
      .setDatabase("postgres")
      .setUser("postgres")
      .setPassword("postgrespw");
    PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
    PgPool pool = PgPool.pool(vertx, pgConnectOptions, poolOptions);
    ObjectMapper mapper = io.vertx.core.json.jackson.DatabindCodec.mapper();
    mapper.registerModule(new JavaTimeModule());
    vertx.deployVerticle(new DataTest2(pool));
  }
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
//    pool.getConnection().compose(conn -> {
//      JsonObject info = JsonObject.of("address", "xxxxx", "labels", List.of("a", "b", "c"));
//      User user = new User(2, UUID.randomUUID().toString(), info, OffsetDateTime.now());
//      return SqlTemplate.forUpdate(conn, "INSERT INTO learn.user(id, name, age, info, create_time) VALUES(#{id}, #{name}, #{age}, #{info}, #{create_time})")
//        .execute(Map.of("id", 3,
//          "name", "jack",
//          "info", info,
//          "age", 18,
//          "create_time", OffsetDateTime.now()));
//    }).onSuccess(result -> System.out.println(result))
//      .onFailure(Throwable::printStackTrace);
    pool.getConnection().compose(conn -> {
        return SqlTemplate.forQuery(conn, "SELECT * from learn.user where id=#{id}")
          .mapTo(User.class)
          .execute(Map.of("id", 3));
      }).onSuccess(result -> {
        result.forEach(System.out::println);
      })
      .onFailure(Throwable::printStackTrace);
  }
}

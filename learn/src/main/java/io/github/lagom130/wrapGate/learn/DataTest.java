package io.github.lagom130.wrapGate.learn;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlClient;

public class DataTest extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new DataTest());
  }
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    PgConnectOptions  pgConnectOptions = new PgConnectOptions();
    pgConnectOptions
      .setPort(32769)
      .setHost("localhost")
      .setDatabase("postgres")
      .setUser("postgres")
      .setPassword("postgrespw");
    PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
    PgPool pool = PgPool.pool(vertx, pgConnectOptions, poolOptions);
    pool.getConnection().compose(conn -> {
        return conn.query("select * from learn.user where id =1")
          .execute()
          .onComplete(rowSetAsyncResult -> {
            if (rowSetAsyncResult.succeeded()){
              System.out.println(rowSetAsyncResult.result().size());
              RowSet<Row> next = rowSetAsyncResult.result();
              System.out.println(next);
            } else {
              rowSetAsyncResult.cause().printStackTrace();
            }
            conn.close();
          });
      }).onComplete(ar -> {
      if (ar.succeeded()) {

        System.out.println("Done");
      } else {
        System.out.println("Something went wrong " + ar.cause().getMessage());
      }
    });
  }
}

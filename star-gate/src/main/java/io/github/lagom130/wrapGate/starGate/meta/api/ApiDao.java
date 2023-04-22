package io.github.lagom130.wrapGate.starGate.meta.api;

import io.netty.util.internal.StringUtil;
import io.vertx.core.Future;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.templates.SqlTemplate;

import java.util.Map;
import java.util.UUID;

public class ApiDao {

  private PgPool pool;

  public ApiDao(PgPool pool) {
    this.pool = pool;
  }

  public Future<ApiDO> getById(String id) {
    return pool.getConnection().compose(conn -> {
        return SqlTemplate.forQuery(conn, "select id, name, host, port, path, method, enabled, tenant_id, updated_time, created_time from gateway.api where id=#{id}")
          .mapTo(ApiDO.class)
          .execute(Map.of("id", id)).onComplete(ar ->conn.close());
      }).compose(rs -> rs.size() == 1 ? Future.succeededFuture(rs.iterator().next()): Future.failedFuture(new Throwable("not found")));
  }

  public Future<Void> add(ApiInputBO inputBO) {
    inputBO.setId(UUID.randomUUID().toString());
    if (StringUtil.isNullOrEmpty(inputBO.getTenantId())) {
      inputBO.setTenantId("default");
    }
    return pool.getConnection().compose(conn -> {
        return SqlTemplate.forUpdate(conn, "insert into gateway.api (id, name, host, port, path, method, enabled, tenant_id, updated_time, created_time) values(#{id}, #{name}, #{host}, #{port}, #{path}, #{method}, #{enabled}, #{tenanId}, now(), now())")
          .mapFrom(ApiInputBO.class)
          .execute(inputBO);
      }).compose(rs -> rs.rowCount() == 1 ? Future.succeededFuture() : Future.failedFuture("insert failed"));
  }

  public Future<Void> deleteById(String id) {
    return pool.getConnection().compose(conn -> {
      return SqlTemplate.forUpdate(conn, "delete form gateway.api where id = #{id}")
        .execute(Map.of("id", id));
    }).compose(rs -> rs.rowCount() == 1 ? Future.succeededFuture() : Future.failedFuture("delete failed"));
  }
}

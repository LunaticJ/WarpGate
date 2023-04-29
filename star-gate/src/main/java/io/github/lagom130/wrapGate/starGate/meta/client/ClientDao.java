package io.github.lagom130.wrapGate.starGate.meta.client;

import io.github.lagom130.wrapGate.starGate.meta.api.ApiInputBO;
import io.github.lagom130.wrapGate.starGate.util.JsonMapper;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.SqlResult;
import io.vertx.sqlclient.templates.SqlTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class ClientDao {

  private PgPool pool;

  private final static String TABLE = "gateway.client";
  private final static String COLUMNS = "id, api_id, client_id, secret, tenant_id, updated_time, created_time";
  private final static String GET_BY_ID = "SELECT "+ COLUMNS +" FROM "+TABLE+" WHERE id=#{id} and tenant_id=#{tenantId}";
  private final static String GET_LIST = "SELECT "+ COLUMNS +" FROM "+TABLE+" WHERE tenant_id=#{tenantId}";
  private final static String INSERT = "INSERT INTO "+TABLE+" ("+COLUMNS+") VALUES(#{id}, #{name}, #{host}, #{port}, #{path}, #{method}, #{enabled}, #{tenanId}, now(), now())";
  private final static String DELETE_BY_ID = "DELETE FROM "+TABLE+" WHERE id=#{id} and tenant_id=#{tenantId}";
  private final static String SUBSCRIPTION_TABLE = "gateway.subscription";
  private final static String SUBSCRIPTION_COLUMNS = "id, api_id, client_id_expire_time, updated_time, created_time";

  public ClientDao(PgPool pool) {
    this.pool = pool;
  }



  public Future<ClientDO> getById(String id) {
    String tenantId = Optional.ofNullable((String) Vertx.vertx().getOrCreateContext().get("tenantId")).orElse("default");
    return pool.getConnection().compose(conn -> SqlTemplate.forQuery(conn, GET_BY_ID)
      .mapTo(ClientDO.class)
      .execute(Map.of("id", id, "tenantId", Optional.ofNullable(tenantId).orElse("default")))
      .onComplete(ar -> conn.close())).compose(rs -> rs.size() == 1 ? Future.succeededFuture(rs.iterator().next()) : Future.failedFuture(new Throwable("not found")));
  }

  public Future<List<ClientDO>> getList() {
    String tenantId = Optional.ofNullable((String)Vertx.vertx().getOrCreateContext().get("tenantId")).orElse("default");
    return pool.getConnection().compose(conn -> SqlTemplate.forQuery(conn, GET_LIST)
      .collecting(Collectors.mapping(row -> JsonMapper.mapper(row, ClientDO.class), Collectors.toList()))
      .execute(Map.of("tenantId", Optional.ofNullable(tenantId).orElse("default"))).onComplete(ar -> conn.close())).map(SqlResult::value);
  }

  public Future<Void> add(ClientInputBO inputBO) {
    String tenantId = Optional.ofNullable((String)Vertx.vertx().getOrCreateContext().get("tenantId")).orElse("default");
    inputBO.setId(UUID.randomUUID().toString());
    inputBO.setTenantId(tenantId);
    return pool.getConnection().compose(conn -> SqlTemplate.forUpdate(conn, INSERT)
      .mapFrom(ClientInputBO.class)
      .execute(inputBO)).compose(rs -> rs.rowCount() == 1 ? Future.succeededFuture() : Future.failedFuture("insert failed"));
  }

  public Future<Void> deleteById(String id) {
    String tenantId = Optional.ofNullable((String)Vertx.vertx().getOrCreateContext().get("tenantId")).orElse("default");
    return pool.getConnection().compose(conn -> SqlTemplate.forUpdate(conn, DELETE_BY_ID)
        .execute(Map.of("id", id, "tenantId", Optional.ofNullable(tenantId).orElse("default"))))
      .compose(rs -> rs.rowCount() == 1 ? Future.succeededFuture() : Future.failedFuture("delete failed"));
  }
}

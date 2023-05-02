package io.github.lagom130.wrapGate.starGate.meta.api;

import io.github.lagom130.wrapGate.starGate.meta.api.bo.ApiInputBO;
import io.github.lagom130.wrapGate.starGate.meta.api.entity.ApiDO;
import io.github.lagom130.wrapGate.starGate.meta.api.entity.SubDO;
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

public class ApiDao {

  private PgPool pool;
  private Vertx vertx;

  private final static String TABLE = "gateway.api";
  private final static String COLUMNS = "id, name, host, port, path, method, enabled, token_bucket_limit, tenant_id, updated_time, created_time";
  private final static String GET_BY_ID = "SELECT "+ COLUMNS +" FROM "+TABLE+" WHERE id=#{id} and tenant_id=#{tenantId}";
  private final static String GET_LIST = "SELECT "+ COLUMNS +" FROM "+TABLE+" WHERE tenant_id=#{tenantId}";
  private final static String INSERT = "INSERT INTO "+TABLE+" ("+COLUMNS+") VALUES(#{id}, #{name}, #{host}, #{port}, #{path}, #{method}, #{enabled}, #{tenantId}, now(), now())";
  private final static String DELETE_BY_ID = "DELETE FROM "+TABLE+" WHERE id=#{id} and tenant_id=#{tenantId}";
  private final static String GET_CLIENTS_BY_ID ="SELECT s.id, c.id as client_id, c.name as name, c.updated_time as updated_time, c.created_time as created_time, s.expire_time as expire, s.max_per_day as max_per_day, s.priority as priority FROM gateway.client as c inner join gateway.subscription as s on c.id = s.client_id and s.api_id = #{apiId}";


  public ApiDao(Vertx vertx, PgPool pool) {
    this.pool = pool;
    this.vertx = vertx;
  }


  public Future<ApiDO> getById(String id) {
    String tenantId = Optional.ofNullable((String)vertx.getOrCreateContext().get("tenantId")).orElse("default");
    return pool.getConnection().compose(conn -> SqlTemplate.forQuery(conn, GET_BY_ID)
      .mapTo(ApiDO.class)
      .execute(Map.of("id", id, "tenantId", Optional.ofNullable(tenantId).orElse("default")))
      .onComplete(ar -> conn.close())).compose(rs -> rs.size() == 1 ? Future.succeededFuture(rs.iterator().next()) : Future.failedFuture(new Throwable("not found")));
  }

  public Future<List<ApiDO>> getList() {
    String tenantId = Optional.ofNullable((String)vertx.getOrCreateContext().get("tenantId")).orElse("default");
    return pool.getConnection().compose(conn -> SqlTemplate.forQuery(conn, GET_LIST)
      .collecting(Collectors.mapping(row -> JsonMapper.mapper(row, ApiDO.class), Collectors.toList()))
      .execute(Map.of("tenantId", Optional.ofNullable(tenantId).orElse("default"))).onComplete(ar -> conn.close())).map(SqlResult::value);
  }

  public Future<Void> add(ApiInputBO inputBO) {
    String tenantId = Optional.ofNullable((String)vertx.getOrCreateContext().get("tenantId")).orElse("default");
    inputBO.setId(UUID.randomUUID().toString());
    inputBO.setTenantId(tenantId);
    return pool.getConnection().compose(conn -> SqlTemplate.forUpdate(conn, INSERT)
      .mapFrom(ApiInputBO.class)
      .execute(inputBO)).compose(rs -> rs.rowCount() == 1 ? Future.succeededFuture() : Future.failedFuture("insert failed"));
  }

  public Future<Void> deleteById(String id) {
    String tenantId = Optional.ofNullable((String)vertx.getOrCreateContext().get("tenantId")).orElse("default");
    return pool.getConnection().compose(conn -> SqlTemplate.forUpdate(conn, DELETE_BY_ID)
      .execute(Map.of("id", id, "tenantId", Optional.ofNullable(tenantId).orElse("default"))))
      .compose(rs -> rs.rowCount() == 1 ? Future.succeededFuture() : Future.failedFuture("delete failed"));
  }


  public Future<List<SubDO>> getSubscriptionClientList(String apiId) {
    return pool.getConnection().compose(conn -> SqlTemplate.forQuery(conn, GET_CLIENTS_BY_ID)
      .collecting(Collectors.mapping(row -> JsonMapper.mapper(row, SubDO.class), Collectors.toList()))
      .execute(Map.of("apiId", apiId)).onComplete(ar -> conn.close())).map(SqlResult::value);
  }
}

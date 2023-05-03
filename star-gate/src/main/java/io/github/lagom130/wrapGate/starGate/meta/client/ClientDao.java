package io.github.lagom130.wrapGate.starGate.meta.client;

import io.github.lagom130.wrapGate.starGate.meta.client.bo.ClientInputBO;
import io.github.lagom130.wrapGate.starGate.meta.client.bo.SubApiInputBO;
import io.github.lagom130.wrapGate.starGate.meta.client.entity.ClientDO;
import io.github.lagom130.wrapGate.starGate.meta.client.entity.SubApiDO;
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

  private Vertx vertx;

  private PgPool pool;

  private final static String TABLE = "gateway.client";
  private final static String COLUMNS = "name, key, secret, tenant_id, updated_time, created_time";
  private final static String COLUMNS_WITH_ID = "id, name, key, secret, tenant_id, updated_time, created_time";
  private final static String GET_BY_ID = "SELECT "+ COLUMNS_WITH_ID +" FROM "+TABLE+" WHERE id=#{id} and tenant_id=#{tenantId}";
  private final static String GET_LIST = "SELECT "+ COLUMNS_WITH_ID +" FROM "+TABLE+" WHERE tenant_id=#{tenantId}";
  private final static String INSERT = "INSERT INTO "+TABLE+" ("+COLUMNS+") VALUES(#{name}, #{key}, #{secret},  #{tenantId}, now(), now())";
  private final static String DELETE_BY_ID = "DELETE FROM "+TABLE+" WHERE id=#{id} and tenant_id=#{tenantId}";
  private final static String GET_APIS_BY_CLIENT_ID ="SELECT s.id, a.id as api_id, a.name as name, a.host as host, a.port as port, a.path as path, a.method as method, a.updated_time as updated_time, a.created_time as created_time, s.expire_time as expire, s.max_per_day as max_per_day, s.priority as priority FROM gateway.api as a inner join gateway.subscription as s on a.id = s.api_id and s.client_id = #{clientId}";
  private final static String DELETE_SUBSCRIPTION_BY_ID ="DELETE FROM gateway.subscription WHERE id=#{id}";
  private final static String INSERT_SUBSCRIPTION ="INSERT INTO gateway.subscription (api_id, client_id, max_per_day, expire_time) VALUES(api_id=#{apiId}, client_id=#{clientId},max_per_day=#{maxPerDay},expire_time=#{expireTime})";


  public ClientDao(Vertx vertx, PgPool pool) {
    this.vertx = vertx;
    this.pool = pool;
  }



  public Future<ClientDO> getById(String id) {
    String tenantId = Optional.ofNullable((String) vertx.getOrCreateContext().get("tenantId")).orElse("default");
    return pool.getConnection().compose(conn -> SqlTemplate.forQuery(conn, GET_BY_ID)
      .mapTo(ClientDO.class)
      .execute(Map.of("id", id, "tenantId", Optional.ofNullable(tenantId).orElse("default")))
      .onComplete(ar -> conn.close())).compose(rs -> rs.size() == 1 ? Future.succeededFuture(rs.iterator().next()) : Future.failedFuture(new Throwable("not found")));
  }

  public Future<List<ClientDO>> getList() {
    String tenantId = Optional.ofNullable((String)vertx.getOrCreateContext().get("tenantId")).orElse("default");
    return pool.getConnection().compose(conn -> SqlTemplate.forQuery(conn, GET_LIST)
      .collecting(Collectors.mapping(row -> JsonMapper.mapper(row, ClientDO.class), Collectors.toList()))
      .execute(Map.of("tenantId", Optional.ofNullable(tenantId).orElse("default"))).onComplete(ar -> conn.close())).map(SqlResult::value);
  }

  public Future<Void> add(ClientInputBO inputBO) {
    String tenantId = Optional.ofNullable((String)vertx.getOrCreateContext().get("tenantId")).orElse("default");
    inputBO.setId(UUID.randomUUID().toString());
    inputBO.setTenantId(tenantId);
    return pool.getConnection().compose(conn -> SqlTemplate.forUpdate(conn, INSERT)
      .mapFrom(ClientInputBO.class)
      .execute(inputBO)).compose(rs -> rs.rowCount() == 1 ? Future.succeededFuture() : Future.failedFuture("insert failed"));
  }

  public Future<Void> deleteById(String id) {
    String tenantId = Optional.ofNullable((String)vertx.getOrCreateContext().get("tenantId")).orElse("default");
    return pool.getConnection().compose(conn -> SqlTemplate.forUpdate(conn, DELETE_BY_ID)
        .execute(Map.of("id", id, "tenantId", Optional.ofNullable(tenantId).orElse("default"))))
      .compose(rs -> rs.rowCount() == 1 ? Future.succeededFuture() : Future.failedFuture("delete failed"));
  }

  // ---------------------------------------------- subscription ----------------------------------------------
  public Future<List<SubApiDO>> getApiList(String clientId) {
    return pool.getConnection().compose(conn -> SqlTemplate.forQuery(conn, GET_APIS_BY_CLIENT_ID)
      .collecting(Collectors.mapping(row -> JsonMapper.mapper(row, SubApiDO.class), Collectors.toList()))
      .execute(Map.of("clientId", clientId)).onComplete(ar -> conn.close())).map(SqlResult::value);
  }

  public Future<Void> addSubscription(SubApiInputBO inputBO) {
    return pool.getConnection().compose(conn -> SqlTemplate.forUpdate(conn, INSERT_SUBSCRIPTION)
      .mapFrom(SubApiInputBO.class)
      .execute(inputBO)).compose(rs -> rs.rowCount() == 1 ? Future.succeededFuture() : Future.failedFuture("insert failed"));
  }

  public Future<Void> deleteSubscription(String id) {
    return pool.getConnection().compose(conn -> SqlTemplate.forUpdate(conn, DELETE_SUBSCRIPTION_BY_ID)
        .execute(Map.of("id", id)))
      .compose(rs -> rs.rowCount() == 1 ? Future.succeededFuture() : Future.failedFuture("delete failed"));
  }
}

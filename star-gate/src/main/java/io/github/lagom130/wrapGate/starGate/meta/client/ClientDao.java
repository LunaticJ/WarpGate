package io.github.lagom130.wrapGate.starGate.meta.client;

import io.github.lagom130.wrapGate.starGate.meta.api.ApiDO;
import io.github.lagom130.wrapGate.starGate.meta.api.ApiInputBO;
import io.github.lagom130.wrapGate.starGate.util.JsonMapper;
import io.netty.util.internal.StringUtil;
import io.vertx.core.Future;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.SqlResult;
import io.vertx.sqlclient.templates.SqlTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ClientDao {

  private PgPool pool;

  public ClientDao(PgPool pool) {
    this.pool = pool;
  }



  public Future<ApiDO> getById(String id) {
    return null;
  }

  public Future<List<ApiDO>> getList() {
    return null;
  }

  public Future<Void> add(ApiInputBO inputBO) {
    return null;
  }

  public Future<Void> deleteById(String id) {
    return null;
  }
}

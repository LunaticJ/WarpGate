package io.github.lagom130.wrapGate.starGate.meta.api;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.pgclient.PgPool;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

public class ApiRoute {
  private ApiDao apiDao;

  public ApiRoute(PgPool pool) {
    this.apiDao = new ApiDao(pool);
  }

  public void addRoute( Router router) {
    router.get("/meta/apis").handler(this::getList);
    router.get("/meta/apis/:id").handler(this::getOne);
    router.get("/meta/apis/:id/clients").handler(this::getClients);
    router.delete("/meta/apis/:id").handler(this::deleteOne);
    router.put("/meta/apis/:id").consumes("*/json").handler(BodyHandler.create()).handler(this::updateOne);
    router.post("/meta/apis").consumes("*/json").handler(BodyHandler.create()).handler(this::addOne);
  }

  public void getOne(RoutingContext ctx){
    String id = ctx.pathParam("id");
    apiDao.getById(id)
      .onSuccess(apiDO -> {
        ApiDTO apiDTO = new ApiDTO();
        try {
          PropertyUtils.copyProperties(apiDTO, apiDO);
        } catch (Exception e) {
          e.printStackTrace();
        }
        ctx.end(JsonObject.mapFrom(apiDTO).toString());
      })
        .onFailure(throwable -> {
          throwable.printStackTrace();
          ctx.end(throwable.getMessage());
        });
  }

  public void getList(RoutingContext ctx){
    //todo:
    ctx.end();
  }

  public void getClients(RoutingContext ctx){
    String id = ctx.pathParam("id");
    //todo:
    ctx.end();
  }

  public void addOne(RoutingContext ctx){
    ApiInputDTO inputDTO = ctx.body().asPojo(ApiInputDTO.class);
    ApiInputBO inputBO = new ApiInputBO();
    try {
      BeanUtils.copyProperties(inputBO, inputDTO);
    } catch (Exception e) {
      e.printStackTrace();
    }
    apiDao.add(inputBO).onSuccess(v ->ctx.response().setStatusCode(204).end()).onFailure(throwable -> {
      throwable.printStackTrace();
      ctx.end(throwable.getMessage());
    });
  }

  public void updateOne(RoutingContext ctx){
    String id = ctx.pathParam("id");
    //todo:
    ctx.end();
  }

  public void deleteOne(RoutingContext ctx){
    String id = ctx.pathParam("id");
    apiDao.deleteById(id)
      .onSuccess(apiDO -> ctx.end(JsonObject.mapFrom(apiDO).toString()))
      .onFailure(throwable -> {
        throwable.printStackTrace();
        ctx.end(throwable.getMessage());
      });
  }
}

package io.github.lagom130.wrapGate.starGate.meta.client;

import io.github.lagom130.wrapGate.starGate.meta.client.bo.ClientInputBO;
import io.github.lagom130.wrapGate.starGate.meta.client.bo.SubApiInputBO;
import io.github.lagom130.wrapGate.starGate.meta.client.dto.*;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.pgclient.PgPool;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.stream.Collectors;

public class ClientRoute {

  private Vertx vertx;
  private ClientDao clientDao;

  public ClientRoute(Vertx vertx, PgPool pool) {
    this.clientDao = new ClientDao(vertx, pool);
  }

  public void addRoute( Router router) {
    router.get("/meta/clients").handler(this::getList);
    router.get("/meta/clients/:id").handler(this::getOne);
    router.delete("/meta/clients/:id").handler(this::deleteOne);
    router.put("/meta/clients/:id").consumes("*/json").handler(BodyHandler.create()).handler(this::updateOne);
    router.post("/meta/clients").consumes("*/json").handler(BodyHandler.create()).handler(this::addOne);
    // subscription
    router.get("/meta/clients/:id/apis").handler(this::getApis);
    router.put("/meta/clients/:id/apis/:apiId").handler(this::subscriptionApis);
    router.delete("/meta/subscription/:id").handler(this::unSubscriptionApis);
  }

  public void getOne(RoutingContext ctx){
    String id = ctx.pathParam("id");
    clientDao.getById(id)
      .onSuccess(clientDO -> {
        ClientFullDTO clientFullDTO = new ClientFullDTO();
        try {
          PropertyUtils.copyProperties(clientFullDTO, clientDO);
        } catch (Exception e) {
          e.printStackTrace();
        }
        ctx.end(JsonObject.mapFrom(clientFullDTO).toString());
      })
        .onFailure(throwable -> {
          throwable.printStackTrace();
          ctx.end(throwable.getMessage());
        });
  }

  public void getList(RoutingContext ctx){
    clientDao.getList().onSuccess(list -> ctx.end(list.stream().map(clientDO -> {
        ClientDTO clientDTO = new ClientDTO();
        try {
          PropertyUtils.copyProperties(clientDTO, clientDO);
        } catch (Exception e) {
          e.printStackTrace();
        }
        return JsonObject.mapFrom(clientDTO);
      }).collect(Collectors.toList()).toString()))
        .onFailure(throwable -> {
          throwable.printStackTrace();
          ctx.end(throwable.getMessage());
        });
  }

  public void addOne(RoutingContext ctx){
    ClientInputDTO inputDTO = ctx.body().asPojo(ClientInputDTO.class);
    ClientInputBO inputBO = new ClientInputBO();
    try {
      BeanUtils.copyProperties(inputBO, inputDTO);
    } catch (Exception e) {
      e.printStackTrace();
    }
    clientDao.add(inputBO).onSuccess(v ->ctx.response().setStatusCode(204).end()).onFailure(throwable -> {
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
    clientDao.deleteById(id)
      .onSuccess(apiDO -> ctx.end(JsonObject.mapFrom(apiDO).toString()))
      .onFailure(throwable -> {
        throwable.printStackTrace();
        ctx.end(throwable.getMessage());
      });
  }


  public void getApis(RoutingContext ctx){
    String id = ctx.pathParam("id");
    clientDao.getApiList(id).onSuccess(list -> ctx.end(list.stream().map(subApiDO -> {
      SubApiDTO dto = new SubApiDTO();
      try {
        PropertyUtils.copyProperties(dto, subApiDO);
      } catch (Exception e) {
        e.printStackTrace();
      }
      return JsonObject.mapFrom(dto);
    }).collect(Collectors.toList()).toString()))
      .onFailure(throwable -> {
        throwable.printStackTrace();
        ctx.end(throwable.getMessage());
      });
  }


  public void subscriptionApis(RoutingContext ctx){
    String id = ctx.pathParam("id");
    String apiId = ctx.pathParam("apiId");
    SubApiInputDTO inputDTO = ctx.body().asPojo(SubApiInputDTO.class);
    SubApiInputBO inputBO = new SubApiInputBO();
    inputBO.setApiId(apiId);
    inputBO.setClientId(id);
    try {
      BeanUtils.copyProperties(inputBO, inputDTO);
    } catch (Exception e) {
      e.printStackTrace();
    }
    clientDao.addSubscription(inputBO).onSuccess(v ->ctx.response().setStatusCode(204).end()).onFailure(throwable -> {
      throwable.printStackTrace();
      ctx.end(throwable.getMessage());
    });
  }


  public void unSubscriptionApis(RoutingContext ctx){
    String id = ctx.pathParam("id");
    clientDao.deleteSubscription(id).onSuccess(v ->ctx.response().setStatusCode(204).end()).onFailure(throwable -> {
      throwable.printStackTrace();
      ctx.end(throwable.getMessage());
    });
  }
}

package io.github.lagom130.wrapGate.starGate.meta.route;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class ApiRoute {
  private Vertx vertx;

  public ApiRoute(Vertx vertx) {
    this.vertx = vertx;
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
    //todo:
    ctx.end();
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
    //todo:
    ctx.end();
  }

  public void updateOne(RoutingContext ctx){
    String id = ctx.pathParam("id");
    //todo:
    ctx.end();
  }

  public void deleteOne(RoutingContext ctx){
    String id = ctx.pathParam("id");
    //todo:
    ctx.end();
  }
}

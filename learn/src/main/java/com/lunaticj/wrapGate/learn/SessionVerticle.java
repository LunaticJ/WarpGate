package com.lunaticj.wrapGate.learn;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

import java.util.UUID;

public class SessionVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new SessionVerticle());
  }

  @Override
  public void start(Promise<Void> startPromise) {
    Router router = Router.router(vertx);
    router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
    router.route().handler(rc -> {
      Session session = rc.session();
      UUID uuid = session.get("uuid");
      if (uuid == null) {
        session.put("uuid", UUID.randomUUID());
        System.out.println("put new UUID");
      } else  {
        System.out.println(session.get("uuid").toString());
      }
      rc.end();
    });



    vertx.createHttpServer().requestHandler(router).listen(9527)
      .onSuccess(httpServer -> System.out.println("server start at port "+httpServer.actualPort()))
      .onFailure(Throwable::printStackTrace);
  }
}

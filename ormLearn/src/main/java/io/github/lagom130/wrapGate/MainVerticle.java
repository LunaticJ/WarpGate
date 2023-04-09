package io.github.lagom130.wrapGate;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.hibernate.reactive.stage.Stage;

import javax.persistence.Persistence;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

public class MainVerticle extends AbstractVerticle {
  Stage.SessionFactory sessionFactory;

  public MainVerticle(Stage.SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.executeBlocking(promise -> {
      Stage.SessionFactory sessionFactory = Persistence.createEntityManagerFactory("example")
        .unwrap(Stage.SessionFactory.class);
      vertx.deployVerticle(new MainVerticle(sessionFactory));
    });

  }


  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    sessionFactory.withSession(session -> session.createQuery("from Author", Author.class).getResultList())
      .toCompletableFuture()
      .whenComplete((authors, throwable) -> {
      if (throwable != null) {
        throwable.printStackTrace();
      } else {
        System.out.println(authors.size());
        authors.forEach(author -> {
          System.out.println(author);
        });
      }
    });
//    Map<Object, Object> properties = Map.of(
//
//    );


  }
}


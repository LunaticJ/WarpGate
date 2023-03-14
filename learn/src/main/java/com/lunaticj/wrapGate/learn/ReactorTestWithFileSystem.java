package com.lunaticj.wrapGate.learn;

import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;
import io.vertx.core.file.OpenOptions;

public class ReactorTestWithFileSystem extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new ReactorTestWithFileSystem());
  }

  @Override
  public void start(Promise<Void> startPromise) {
    FileSystem fileSystem = vertx.fileSystem();
    Future<Void> f1 = fileSystem.createFile("text1.txt")
      .compose(v -> {
        System.out.println("f1 point 1");
        return fileSystem.open("text1.txt", new OpenOptions().setWrite(true));
      })
      .compose(asyncFile -> {
          System.out.println("f1 point 2");
          return asyncFile.write(Buffer.buffer("write test1"))
            .onSuccess(v -> {
              System.out.println("f1 point 3");
              asyncFile.read(Buffer.buffer(), 0, 0, 16)
                .onSuccess(buffer -> {
                  System.out.println("f1 point 4");
                  System.out.println(buffer);
                });
            });
        }
      );
    Future<Void> f2 = fileSystem.createFile("text2.txt").onComplete(v -> System.out.println("f2 complete"));
    CompositeFuture f3 = CompositeFuture.all(f1, f2);
    f3.onSuccess(cf -> System.out.println("f3 success")).onFailure(throwable -> {
      if (f1.failed()) {
        f1.cause().printStackTrace();
      }
      if (f2.failed()) {
        f2.cause().printStackTrace();
      }
    });
    System.out.println("on the end");


  }
}

package com.lunaticj.wrapGate.templar;

import io.vertx.core.Launcher;
import io.vertx.core.VertxOptions;

import java.util.concurrent.TimeUnit;

public class ApplicationLauncher extends Launcher {

  public static void main(String[] args) {
    ApplicationLauncher applicationLauncher = new ApplicationLauncher();
    applicationLauncher.dispatch(args);
  }

  @Override
  public void beforeStartingVertx(VertxOptions options) {
//    options.setWarningExceptionTime(10L*1000);
    options.setBlockedThreadCheckInterval(2L*1000);
//    options.setBlockedThreadCheckIntervalUnit(TimeUnit.MILLISECONDS);
    options.setMaxEventLoopExecuteTime(10L*1000);
    super.beforeStartingVertx(options);
  }
}

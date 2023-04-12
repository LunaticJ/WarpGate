package io.github.lagom130.warpGate.overlord;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import io.vertx.core.json.JsonObject;

import java.util.Map;

public class DemoListener implements ReadListener<DemoData> {
  @Override
  public void invokeHead(Map headMap, AnalysisContext context) {
    System.out.println(headMap);
    ReadListener.super.invokeHead(headMap, context);
  }

  @Override
  public void invoke(DemoData demoData, AnalysisContext analysisContext) {
    System.out.println(JsonObject.mapFrom(demoData).toString());
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext analysisContext) {

  }
}

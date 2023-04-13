package io.github.lagom130.warpGate.overlord;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import io.netty.util.internal.StringUtil;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DemoListener implements ReadListener<DemoData> {
  private List<RealDemoData>  rdd = new ArrayList();
  private RealDemoData temp = null;
  @Override
  public void invokeHead(Map headMap, AnalysisContext context) {
    System.out.println(headMap);
    ReadListener.super.invokeHead(headMap, context);
  }

  @Override
  public void invoke(DemoData demoData, AnalysisContext analysisContext) {
    if (StringUtil.isNullOrEmpty(demoData.getK1()) || (temp != null && temp.getK1().equals(demoData.getK1()))) {
      if(!StringUtil.isNullOrEmpty(demoData.getK3a())) {
        K3 nk3 = new K3(demoData.getK3a(), demoData.getK3b(), demoData.getK3c());
        List<K3> k3 = temp.getK3();
        List<K3> newK3L = new ArrayList<>();
        k3.forEach(k -> {
          newK3L.add(k);
        });
        newK3L.add(nk3);
        temp.setK3(newK3L);
      }
      if(!StringUtil.isNullOrEmpty(demoData.getK5a())) {
        K5 nk5 = new K5(demoData.getK5a(), demoData.getK5b());
        List<K5> k5 = temp.getK5();
        k5.add(nk5);
        temp.setK5(k5);
      }
    } else if(temp != null)  {
      rdd.add(JsonObject.mapFrom(temp).mapTo(RealDemoData.class));
      temp = demoData.toRDD();
    } else {
      temp = demoData.toRDD();
    }
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    JsonObject jsonObject = JsonObject.mapFrom(temp);
    RealDemoData realDemoData = jsonObject.mapTo(RealDemoData.class);
    rdd.add(realDemoData);
    temp = null;
    System.out.println(rdd);
  }

  @Override
  public boolean hasNext(AnalysisContext context) {
    System.out.println(ReadListener.super.hasNext(context));
    return ReadListener.super.hasNext(context);
  }


}

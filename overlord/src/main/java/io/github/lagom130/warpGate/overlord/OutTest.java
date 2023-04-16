package io.github.lagom130.warpGate.overlord;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OutTest {
  public static void main(String[] args) {
    String outPath =  "\\opt\\out.xlsx";
    String templatePath =  "\\opt\\template.xlsx";
    List<DemoData> dataList = new ArrayList<>();
    List<Map<String, String>> msgList = new ArrayList<>();
    for(int i=0;i<1000;i++) {
      DemoData demoData = new DemoData();
      int j = 0;
      demoData.setK1("k1值行"+i+", 哈哈哈哈"+(++j));
      demoData.setK2("k2值行"+i+", 哈哈哈哈"+(++j));
      demoData.setK3a("k3a值行"+i+", 哈哈哈哈"+(++j));
      demoData.setK3b("k3b值行"+i+", 哈哈哈哈"+(++j));
      demoData.setK3c("k3c值行"+i+", 哈哈哈哈"+(++j));
      demoData.setK4("k4值行"+i+", 哈哈哈哈"+(++j));
      demoData.setK5a(i%2==0 ? Boolean.TRUE : Boolean.FALSE);
      demoData.setK5b("k5b值行"+i+", 哈哈哈哈"+(++j));
      dataList.add(demoData);
      msgList.add(Map.of("msg", ++j+"测了格式，我就看看, "+i));
    }
    String title = "测试导入标题";
    try(ExcelWriter excelWriter = EasyExcel.write(outPath).withTemplate(templatePath).build()){
      WriteSheet writeSheet0 = EasyExcel.writerSheet(0).build();
      WriteSheet writeSheet1 = EasyExcel.writerSheet(1).build();
      FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
      excelWriter.fill(dataList, fillConfig, writeSheet0);
      excelWriter.fill(msgList, fillConfig, writeSheet1);
      excelWriter.fill(Map.of("title", title), writeSheet0);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      System.out.println("finished");
    }


  }
}

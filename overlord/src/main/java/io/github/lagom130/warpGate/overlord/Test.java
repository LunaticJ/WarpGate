package io.github.lagom130.warpGate.overlord;

import com.alibaba.excel.EasyExcel;

import java.util.List;

public class Test {
  public static void main(String[] args) {
//    List<List<String>> heads = List.of(List.of("k1", "k2", "k3", "k4", "k5", "k6", "k7", "k8"));
    List<List<String>> heads = List.of(List.of("k1"), List.of("k2"), List.of("k3"), List.of("k4"), List.of("k5"), List.of("k6"), List.of("k7"), List.of("k8"));
    EasyExcel.read("\\input1.xlsx", DemoData.class, new DemoListener()).sheet(0).headRowNumber(3).head(heads).doRead();
  }
}

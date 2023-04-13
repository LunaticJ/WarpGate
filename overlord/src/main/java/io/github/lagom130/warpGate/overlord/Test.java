package io.github.lagom130.warpGate.overlord;

import com.alibaba.excel.EasyExcel;

import java.util.List;

public class Test {
  public static void main(String[] args) {
    EasyExcel.read("\\input1.xlsx", DemoData.class, new DemoListener()).sheet(0).headRowNumber(4).doRead();
  }
}

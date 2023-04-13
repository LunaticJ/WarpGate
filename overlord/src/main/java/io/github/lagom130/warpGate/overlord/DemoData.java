package io.github.lagom130.warpGate.overlord;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.List;

public class DemoData {
  @ExcelProperty(index = 0)
  private String k1;
  @ExcelProperty(index = 1)
  private String k2;
  @ExcelProperty(index = 2)
  private String k3a;
  @ExcelProperty(index = 3)
  private String k3b;
  @ExcelProperty(index = 4)
  private String k3c;
  @ExcelProperty(index = 5)
  private String k4;
  @ExcelProperty(index = 6)
  private String k5a;
  @ExcelProperty(index = 7)
  private String k5b;

  public String getK1() {
    return k1;
  }

  public void setK1(String k1) {
    this.k1 = k1;
  }

  public String getK2() {
    return k2;
  }

  public void setK2(String k2) {
    this.k2 = k2;
  }

  public String getK3a() {
    return k3a;
  }

  public void setK3a(String k3a) {
    this.k3a = k3a;
  }

  public String getK3b() {
    return k3b;
  }

  public void setK3b(String k3b) {
    this.k3b = k3b;
  }

  public String getK3c() {
    return k3c;
  }

  public void setK3c(String k3c) {
    this.k3c = k3c;
  }

  public String getK4() {
    return k4;
  }

  public void setK4(String k4) {
    this.k4 = k4;
  }

  public String getK5a() {
    return k5a;
  }

  public void setK5a(String k5a) {
    this.k5a = k5a;
  }

  public String getK5b() {
    return k5b;
  }

  public void setK5b(String k5b) {
    this.k5b = k5b;
  }

  public RealDemoData toRDD() {
    RealDemoData rdd = new RealDemoData();
    rdd.setK1(k1);
    rdd.setK2(k2);
    rdd.setK3(List.of(new K3(k3a, k3b, k3c)));
    rdd.setK4(k4);
    rdd.setK5(List.of(new K5(k5a, k5b)));
    return rdd;
  }
}

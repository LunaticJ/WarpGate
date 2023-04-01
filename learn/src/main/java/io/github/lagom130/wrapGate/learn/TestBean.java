package io.github.lagom130.wrapGate.learn;


import com.fasterxml.jackson.annotation.JsonProperty;

public class TestBean {
  @JsonProperty("first_name")
  private String firstName;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
}

package io.github.lagom130.wrapGate.starGate.meta.client.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public class SubApiDTO extends SubApiInputDTO{
  private String id;
  private String apiId;
  private String name;
  private String host;
  private Integer port;
  private String path;
  private String method;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "GMT+8", shape = JsonFormat.Shape.OBJECT)
  private OffsetDateTime updatedTime;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "GMT+8", shape = JsonFormat.Shape.OBJECT)
  private OffsetDateTime createdTime;

  public SubApiDTO() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getApiId() {
    return apiId;
  }

  public void setApiId(String apiId) {
    this.apiId = apiId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public OffsetDateTime getUpdatedTime() {
    return updatedTime;
  }

  public void setUpdatedTime(OffsetDateTime updatedTime) {
    this.updatedTime = updatedTime;
  }

  public OffsetDateTime getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(OffsetDateTime createdTime) {
    this.createdTime = createdTime;
  }
}

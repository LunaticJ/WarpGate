package io.github.lagom130.wrapGate.starGate.meta.client.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public class SubApiDO {
  private String id;
  @JsonProperty("api_id")
  private String apiId;
  private String name;
  private String host;
  private Integer port;
  private String path;
  private String method;
  @JsonProperty("updated_time")
  private OffsetDateTime updatedTime;
  @JsonProperty("created_time")
  private OffsetDateTime createdTime;

  @JsonProperty("max_per_day")
  private Long maxPerDay;
  @JsonProperty("expire_time")
  private OffsetDateTime expireTime;
  /**
   * priority
   * -1 no limit
   * 1 seconds per token 1000 max token 10000
   * 2 seconds per token 100 max token 1000
   * 3 seconds per token 10 max token 100
   * 4 seconds per token 1 max token 10
   */
  private Integer priority;

  public SubApiDO() {
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

  public Long getMaxPerDay() {
    return maxPerDay;
  }

  public void setMaxPerDay(Long maxPerDay) {
    this.maxPerDay = maxPerDay;
  }

  public OffsetDateTime getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(OffsetDateTime expireTime) {
    this.expireTime = expireTime;
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }
}

package io.github.lagom130.wrapGate.starGate.meta.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public class SubDO {
  private String id;

  @JsonProperty("client_id")
  private String clientId;
  private String name;
  @JsonProperty("updated_Time")
  private OffsetDateTime updatedTime;
  @JsonProperty("created_Time")
  private OffsetDateTime createdTime;
  @JsonProperty("max_per_day")
  private Long maxPerDay;
  @JsonProperty("expire_Time")
  private OffsetDateTime expireTime;
  private Integer priority;

  public SubDO() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

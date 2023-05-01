package io.github.lagom130.wrapGate.starGate.meta.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;

public class SubDTO {
  private String id;
  private String clientId;
  private String name;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "GMT+8", shape = JsonFormat.Shape.OBJECT)
  private OffsetDateTime updatedTime;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "GMT+8", shape = JsonFormat.Shape.OBJECT)
  private OffsetDateTime createdTime;

  private Long maxPerDay;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "GMT+8", shape = JsonFormat.Shape.OBJECT)
  private OffsetDateTime expireTime;
  private Integer priority;


  public SubDTO() {
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

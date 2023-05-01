package io.github.lagom130.wrapGate.starGate.meta.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public class ApiDO {
  private String id;
  private String name;
  private String host;
  private Integer port;
  private String path;
  private String method;

  @JsonProperty("max_per_day")
  private Long maxPerDay;
  private Boolean enabled;
  @JsonProperty("tenant_id")
  private String tenantId;
  @JsonProperty("updated_time")
  private OffsetDateTime updatedTime;
  @JsonProperty("created_time")
  private OffsetDateTime createdTime;

  @JsonProperty("token_bucket_limit")
  private TokenBucketLimit tokenBucketLimit;

  public ApiDO() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public Long getMaxPerDay() {
    return maxPerDay;
  }

  public void setMaxPerDay(Long maxPerDay) {
    this.maxPerDay = maxPerDay;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
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

  public TokenBucketLimit getTokenBucketLimit() {
    return tokenBucketLimit;
  }

  public void setTokenBucketLimit(TokenBucketLimit tokenBucketLimit) {
    this.tokenBucketLimit = tokenBucketLimit;
  }
}

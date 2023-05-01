package io.github.lagom130.wrapGate.starGate.meta.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenBucketLimitDTO {
  private Boolean enabled;
  private Integer maxBucket;
  private Integer tokenPerSecond;
  private Integer maxWaitMilliSeconds;

  public TokenBucketLimitDTO() {
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Integer getMaxBucket() {
    return maxBucket;
  }

  public void setMaxBucket(Integer maxBucket) {
    this.maxBucket = maxBucket;
  }

  public Integer getTokenPerSecond() {
    return tokenPerSecond;
  }

  public void setTokenPerSecond(Integer tokenPerSecond) {
    this.tokenPerSecond = tokenPerSecond;
  }

  public Integer getMaxWaitMilliSeconds() {
    return maxWaitMilliSeconds;
  }

  public void setMaxWaitMilliSeconds(Integer maxWaitMilliSeconds) {
    this.maxWaitMilliSeconds = maxWaitMilliSeconds;
  }
}

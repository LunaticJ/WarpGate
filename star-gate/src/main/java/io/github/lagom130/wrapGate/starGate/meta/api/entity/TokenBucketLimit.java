package io.github.lagom130.wrapGate.starGate.meta.api.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenBucketLimit {
  private Boolean enabled;
  @JsonProperty("max_bucket")
  private Integer maxBucket;
  @JsonProperty("token_per_second")
  private Integer tokenPerSecond;
  @JsonProperty("max_wait_milli_seconds")
  private Integer maxWaitMilliSeconds;

  public TokenBucketLimit() {
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

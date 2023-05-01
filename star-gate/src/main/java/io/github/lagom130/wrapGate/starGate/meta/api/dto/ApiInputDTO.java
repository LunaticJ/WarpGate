package io.github.lagom130.wrapGate.starGate.meta.api.dto;

public class ApiInputDTO {
  private String name;
  private String host;
  private Integer port;
  private String path;
  private String method;
  private Boolean enabled;

  private Long maxPerDay;

  private TokenBucketLimitDTO tokenBucketLimit;

  public ApiInputDTO() {
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

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Long getMaxPerDay() {
    return maxPerDay;
  }

  public void setMaxPerDay(Long maxPerDay) {
    this.maxPerDay = maxPerDay;
  }

  public TokenBucketLimitDTO getTokenBucketLimit() {
    return tokenBucketLimit;
  }

  public void setTokenBucketLimit(TokenBucketLimitDTO tokenBucketLimit) {
    this.tokenBucketLimit = tokenBucketLimit;
  }
}

package io.github.lagom130.wrapGate.starGate.meta.client.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.lagom130.wrapGate.starGate.meta.api.dto.ApiInputDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientInputBO extends ApiInputDTO {
  private String id;
  private String tenantId;

  private String key;

  private String secret;


  public ClientInputBO() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }
}

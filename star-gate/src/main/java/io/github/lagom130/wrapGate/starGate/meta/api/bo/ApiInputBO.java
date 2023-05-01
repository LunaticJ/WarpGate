package io.github.lagom130.wrapGate.starGate.meta.api.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.lagom130.wrapGate.starGate.meta.api.dto.ApiInputDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiInputBO extends ApiInputDTO {
  private String id;
  private String tenantId;


  public ApiInputBO() {
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
}

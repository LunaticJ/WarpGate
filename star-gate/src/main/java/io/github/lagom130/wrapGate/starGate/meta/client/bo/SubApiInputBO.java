package io.github.lagom130.wrapGate.starGate.meta.client.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.lagom130.wrapGate.starGate.meta.client.dto.SubApiInputDTO;

import java.time.OffsetDateTime;

public class SubApiInputBO extends SubApiInputDTO {
  private String apiId;
  private String clientId;

  public SubApiInputBO() {
  }

  public String getApiId() {
    return apiId;
  }

  public void setApiId(String apiId) {
    this.apiId = apiId;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }
}

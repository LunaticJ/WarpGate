package io.github.lagom130.wrapGate.starGate.meta.client.dto;

import io.github.lagom130.wrapGate.starGate.meta.client.dto.ClientDTO;

public class ClientFullDTO extends ClientDTO {
  private String secret;

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }
}

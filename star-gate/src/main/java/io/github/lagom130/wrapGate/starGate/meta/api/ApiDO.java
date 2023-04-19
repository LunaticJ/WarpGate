package io.github.lagom130.wrapGate.starGate.meta.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record ApiDO(
  String id,
  String name,
  String host,
  Integer port,
  String path,
  String method,
  String context,
  Boolean enabled,
  @JsonProperty("tenant_id")
  String tenantId,
  @JsonProperty("updated_time")
  OffsetDateTime updatedTime,
  @JsonProperty("created_time")
  OffsetDateTime createdTime) {
}

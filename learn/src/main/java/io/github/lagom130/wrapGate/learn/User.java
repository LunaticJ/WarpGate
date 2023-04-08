package io.github.lagom130.wrapGate.learn;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record User (
  Integer id,
  String name,
  Info info,
  @JsonProperty("create_time")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "GMT+8", shape = JsonFormat.Shape.OBJECT)
  OffsetDateTime createTime
){}

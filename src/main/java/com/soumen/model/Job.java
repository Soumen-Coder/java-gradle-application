package com.soumen.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableJob.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Job {
    @JsonProperty("id")
    String id();
    @JsonProperty("title")
    String title();
    @JsonProperty("status")
    String status();
}

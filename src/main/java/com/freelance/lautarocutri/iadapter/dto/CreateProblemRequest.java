package com.freelance.lautarocutri.iadapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.freelance.lautarocutri.entity.Problem;

public record CreateProblemRequest(
  @JsonProperty(required = false, value = "full_scan") Boolean fullScan,
  Problem problem
) {}

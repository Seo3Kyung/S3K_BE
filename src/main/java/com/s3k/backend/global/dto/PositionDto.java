package com.s3k.backend.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class PositionDto {
  protected String latitude;
  protected String longitude;
}

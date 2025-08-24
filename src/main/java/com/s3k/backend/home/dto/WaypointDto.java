package com.s3k.backend.home.dto;

public class WaypointDto {

  public record Response(
      Long id,
      String title,
      String address
  ) {

  }

}

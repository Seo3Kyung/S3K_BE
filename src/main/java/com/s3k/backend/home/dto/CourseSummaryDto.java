package com.s3k.backend.home.dto;

import java.util.List;

public class CourseSummaryDto {

  public record Response(
      Long id,
      String startPoint,
      String endPoint,
      String title,
      String author,
      boolean like,
      List<String> tags,
      Long distance,
      int waypointCount,
      Long duration
  ) {

  }

}

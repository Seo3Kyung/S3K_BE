package com.s3k.backend.home.dto;

import java.util.List;

public class NearByCoursesDto {

  public record Response(
      Long id,
      Long distance,
      Long totalTime,
      List<String> tags,
      String startPoint,
      String endPoint
  ) {

  }

}

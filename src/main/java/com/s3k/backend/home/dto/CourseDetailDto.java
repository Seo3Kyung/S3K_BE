package com.s3k.backend.home.dto;

import com.s3k.backend.home.entity.Stopover;
import java.util.List;

public class CourseDetailDto {

  public record Response(
      String title,
      boolean like,
      String startPoint,
      String endPoint,
      Long distance,
      int waypointCount,
      Long duration,
      List<String> theme,
      List<Stopover> stopoverList
  ) {

  }

}

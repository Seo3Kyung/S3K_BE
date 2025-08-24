package com.s3k.backend.home.dto;

import java.time.LocalDate;
import java.util.List;

public class RecentCoursesDto {

  public record Response(
      Long id,
      Long distance,
      List<String> tags,
      String startPoint,
      String endPoint,
      LocalDate searchDate
  ) {

  }

}

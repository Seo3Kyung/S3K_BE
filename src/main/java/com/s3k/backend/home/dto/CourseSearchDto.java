package com.s3k.backend.home.dto;

import java.util.List;

public class CourseSearchDto {

  public record Request(
      String startLocation,
      String endLocation,
      Long totalTime,
      List<String> tagList,
      List<String> themeList
  ) {

  }

}

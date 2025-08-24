package com.s3k.backend.member.dto;

public class CategoryDto {

  public record Response(
      Long id,
      String emoji,
      String title
  ) {

  }

}

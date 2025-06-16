package com.s3k.backend.member.dto;

public class MemberSigninDto {

  public record Request(
      String authCode
  ) {

  }
}

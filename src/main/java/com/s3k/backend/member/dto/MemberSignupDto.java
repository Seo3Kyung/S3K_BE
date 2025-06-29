package com.s3k.backend.member.dto;

public class MemberSignupDto {

  public record Request(
      String nickname,
      String gender,
      Integer birthYear,
      String address,
      boolean tos,
      boolean privacyPolicy
  ) {

  }
}

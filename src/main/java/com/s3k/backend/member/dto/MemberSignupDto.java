package com.s3k.backend.member.dto;

public class MemberSignupDto {

  public record Request(
      String snsId,
      String nickname,
      boolean tos,
      boolean privacyPolicy
  ) {

  }
}

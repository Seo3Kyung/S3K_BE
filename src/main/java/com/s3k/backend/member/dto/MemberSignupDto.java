package com.s3k.backend.member.dto;

public class MemberSignupDto {

  public record Request(
      String id,
      String nickname,
      String gender,
      String address,
      Integer birthYear,
      boolean tos,
      boolean privacyPolicy,
      boolean thirdPartyPolicy,
      boolean locationPolicy
  ) {

  }
}

package com.s3k.backend.member.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.s3k.backend.global.EncodedIdDeserializer;

public class MemberSignupDto {

  public record Request(
      @JsonDeserialize(using = EncodedIdDeserializer.class)
      String snsId,
      String nickname,
      boolean tos,
      boolean privacyPolicy
  ) {

  }
}

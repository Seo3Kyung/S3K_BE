package com.s3k.backend.member.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.s3k.backend.global.EncodedIdSerializer;
import com.s3k.backend.member.enums.Role;
import com.s3k.backend.member.enums.Sns;
import com.s3k.backend.member.enums.Status;
import java.time.LocalDateTime;

public class MemberDefaultDto {

  public record Response(
      @JsonSerialize(using = EncodedIdSerializer.class)
      Long memberId,
      @JsonSerialize(using = EncodedIdSerializer.class)
      String snsId,
      Sns sns,
      String nickname,
      boolean tos,
      boolean privacyPolicy,
      LocalDateTime agreeAt,
      Role role,
      Status status,
      LocalDateTime createdAt,
      LocalDateTime updatedAt
  ) {

  }
}

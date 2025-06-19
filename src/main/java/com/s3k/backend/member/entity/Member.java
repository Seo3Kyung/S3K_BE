package com.s3k.backend.member.entity;

import com.s3k.backend.member.dto.MemberSignupDto;
import com.s3k.backend.member.enums.Role;
import com.s3k.backend.member.enums.Sns;
import com.s3k.backend.member.enums.Status;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Member {

  // 서비스 회원 ID
  private Long id;

  // SNS 고유 식별 ID
  private String snsId;

  // SNS 종류
  private int sns;

  // 서비스 회원 닉네임
  private String nickname;

  // 이용 약관
  private boolean tos;

  // 개인정보 수집 및 이용 동의
  private boolean privacyPolicy;

  // 개인정보 수집 및 이용 동의일자
  private LocalDateTime agreeAt;

  // 서비스 회원 권한
  private int role;

  // 서비스 회원 상태
  private int status;

  // 서비스 회원 정보 생성 일자(가입)
  private LocalDateTime createdAt;

  // 서비스 회원 정보 수정 일자
  private LocalDateTime updatedAt;

  // 성별
  private String gender;

  // 연령을 알기 위한 생일년도
  private Integer birthYear;

  // 주된 산책 지역
  private String address;

  public static Member createPendingMember(String snsId, Sns sns) {
    return Member.builder()
        .snsId(snsId)
        .sns(sns.getValue())
        .role(Role.CHECK.getValue())
        .status(Status.PENDING.getValue())
        .build();
  }

  public Member completeSignupProfile(MemberSignupDto.Request dto) {
    return this.toBuilder()
        .nickname(dto.nickname())
        .gender(dto.gender())
        .birthYear(dto.birthYear())
        .address(dto.address())
        .role(Role.MEMBER.getValue())
        .status(Status.ACTIVE.getValue())
        .build();
  }
}

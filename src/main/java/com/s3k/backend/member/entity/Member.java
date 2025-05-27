package com.s3k.backend.member.entity;

import com.s3k.backend.member.enums.Role;
import com.s3k.backend.member.enums.Sns;
import com.s3k.backend.member.enums.Status;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Member {

  private Long id; // 서비스 회원 ID
  private String snsId; // SNS 고유 식별 ID
  private int sns; // SNS 종류
  private String nickname; // 서비스 회원 닉네임
  //  private String memberId; - snsId로 대체
//  private String password; - snsId로 대체
  private boolean tos; // 이용 약관
  private boolean privacyPolicy; // 개인정보 수집 및 이용 동의
  private LocalDateTime agreeAt; // 개인정보 수집 및 이용 동의일자
  private int role; // 서비스 회원 권한
  private int status; // 서비스 회원 상태
  private LocalDateTime createdAt; // 서비스 회원 정보 생성 일자(가입)
  private LocalDateTime updatedAt; // 서비스 회원 정보 수정 일자

  public static Member createPendingMember(String snsId, Sns sns) {
    return Member.builder()
        .snsId(snsId)
        .sns(sns.getValue())
        .role(Role.CHECK.getValue())
        .status(Status.PENDING.getValue())
        .build();
  }
}

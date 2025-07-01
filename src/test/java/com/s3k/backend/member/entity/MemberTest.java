package com.s3k.backend.member.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.s3k.backend.member.dto.MemberSignupDto;
import com.s3k.backend.member.dto.MemberSignupDto.Request;
import com.s3k.backend.member.enums.Role;
import com.s3k.backend.member.enums.Sns;
import com.s3k.backend.member.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberTest {

  private Member pendingMember;

  @BeforeEach
  void setUp() {
    pendingMember = Member.createPendingMember("sns_id", Sns.KAKAO);
  }

  @Test
  @DisplayName("Pending 회원 완전 회원가입 데이터 변환")
  void completeMemberTest() {
    MemberSignupDto.Request requestDto = new Request(
        "nickname",
        "M",
        1993,
        "주소",
        true,
        true
    );

    Member activedMember = pendingMember.completeSignupProfile(requestDto);

    assertAll("변환 결과",
        () -> assertThat(activedMember.getSnsId()).isEqualTo(pendingMember.getSnsId()),
        () -> assertThat(activedMember.getNickname()).isEqualTo(requestDto.nickname()),
        () -> assertThat(activedMember.getGender()).isEqualTo(requestDto.gender()),
        () -> assertThat(activedMember.getBirthYear()).isEqualTo(requestDto.birthYear()),
        () -> assertThat(activedMember.getAddress()).isEqualTo(requestDto.address()),
        () -> assertThat(activedMember.getRole()).isEqualTo(Role.MEMBER.getValue()),
        () -> assertThat(activedMember.getStatus()).isEqualTo(Status.ACTIVE.getValue())
    );
  }

  @Test
  @DisplayName("pending와 actived로 변경해도 기존의 임시 회원의 정보는 건들이지 않아야한다")
  void pendingMemberTest() {
    MemberSignupDto.Request requestDto = new Request(
        "nickname",
        "M",
        1993,
        "주소",
        true,
        true
    );

    Member activedMember = pendingMember.completeSignupProfile(requestDto);

    assertThat(pendingMember.getSnsId()).isEqualTo(activedMember.getSnsId());
  }

}

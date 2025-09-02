package com.s3k.backend.member.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.s3k.backend.member.dto.MemberSignupDto;
import com.s3k.backend.member.enums.Role;
import com.s3k.backend.member.enums.Sns;
import com.s3k.backend.member.enums.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Member 엔티티 테스트")
class MemberTest {

  @Test
  @DisplayName("SNS로 대기 상태 회원 생성")
  void createPendingMemberWithKakao() {
    // given
    String snsId = "kakao12345";
    Sns sns = Sns.KAKAO;

    // when
    Member pendingMember = Member.createPendingMember(snsId, sns);

    // then
    assertThat(pendingMember.getSnsId()).isEqualTo(snsId);
    assertThat(pendingMember.getSns()).isEqualTo(Sns.KAKAO.getValue());
    assertThat(pendingMember.getRole()).isEqualTo(Role.CHECK.getValue());
    assertThat(pendingMember.getStatus()).isEqualTo(Status.PENDING.getValue());

    // 다른 필드들은 null 또는 기본값이어야 함
    assertThat(pendingMember.getId()).isNull();
    assertThat(pendingMember.getNickname()).isNull();
    assertThat(pendingMember.getGender()).isNull();
    assertThat(pendingMember.getAddress()).isNull();
    assertThat(pendingMember.getBirthYear()).isEqualTo(0);
    assertThat(pendingMember.getProfileImageId()).isEqualTo(0L);
  }

  @Test
  @DisplayName("다양한 SNS 타입별 회원 생성 테스트")
  void createMembersWithDifferentSnsTypes() {
    // given & when
    Member kakaoMember = Member.createPendingMember("kakao123", Sns.KAKAO);
    Member naverMember = Member.createPendingMember("naver456", Sns.NAVER);
    Member googleMember = Member.createPendingMember("google789", Sns.GOOGLE);

    // then
    assertThat(kakaoMember.getSns()).isEqualTo(Sns.KAKAO.getValue());
    assertThat(naverMember.getSns()).isEqualTo(Sns.NAVER.getValue());
    assertThat(googleMember.getSns()).isEqualTo(Sns.GOOGLE.getValue());

    // 모든 회원이 동일한 초기 상태를 가져야 함
    assertThat(kakaoMember.getRole()).isEqualTo(Role.CHECK.getValue());
    assertThat(naverMember.getRole()).isEqualTo(Role.CHECK.getValue());
    assertThat(googleMember.getRole()).isEqualTo(Role.CHECK.getValue());

    assertThat(kakaoMember.getStatus()).isEqualTo(Status.PENDING.getValue());
    assertThat(naverMember.getStatus()).isEqualTo(Status.PENDING.getValue());
    assertThat(googleMember.getStatus()).isEqualTo(Status.PENDING.getValue());
  }

  @Test
  @DisplayName("회원가입")
  void completeSignupProfileWithAllAgreements() {
    // given
    Member pendingMember = Member.createPendingMember("kakao123", Sns.KAKAO);

    MemberSignupDto.Request signupRequest = new MemberSignupDto.Request(
        "완성된닉네임",
        "F",
        "부산시 해운대구",
        1995,
        true,
        true,
        true,
        true
    );

    // when
    Member completedMember = pendingMember.completeSignupProfile(signupRequest);

    // then
    // 기존 정보는 유지
    assertThat(completedMember.getSnsId()).isEqualTo(pendingMember.getSnsId());
    assertThat(completedMember.getSns()).isEqualTo(pendingMember.getSns());

    // 새로운 정보는 업데이트
    assertThat(completedMember.getNickname()).isEqualTo("완성된닉네임");
    assertThat(completedMember.getGender()).isEqualTo("F");
    assertThat(completedMember.getAddress()).isEqualTo("부산시 해운대구");
    assertThat(completedMember.getBirthYear()).isEqualTo(1995);

    // 상태와 권한 변경
    assertThat(completedMember.getRole()).isEqualTo(Role.MEMBER.getValue());
    assertThat(completedMember.getStatus()).isEqualTo(Status.ACTIVE.getValue());

    // 동의 항목들
    assertThat(completedMember.isTos()).isTrue();
    assertThat(completedMember.isPrivacyPolicy()).isTrue();
    assertThat(completedMember.isThirdPartyPolicy()).isTrue();
    assertThat(completedMember.isLocationPolicy()).isTrue();
  }

  @Test
  @DisplayName("회원가입할때 동의 항목 체크 안하면 회원가입 불가")
  void completeSignupProfileWithPartialAgreements() {

  }
}

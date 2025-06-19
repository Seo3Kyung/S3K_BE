package com.s3k.backend.member.service;

import com.s3k.backend.member.dto.MemberSignupDto;
import com.s3k.backend.member.entity.Member;
import com.s3k.backend.member.enums.Sns;
import com.s3k.backend.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberMapper memberMapper;

  public boolean existsMemberBySnsId(String snsId) {
    return memberMapper.existsMemberBySnsId(snsId);
  }

  @Transactional
  public void createPendingMember(String snsId, String snsName) {
    Sns sns = Sns.fromRegistrationId(snsName);
    Member pendingMember = Member.createPendingMember(snsId, sns);
    memberMapper.insertPendingMember(pendingMember);
  }

  public Member getMemberDetailBySnsId(String snsId) {
    this.ensureMemberExists(snsId);

    return memberMapper.getMemberDetailBySnsId(snsId);
  }

  @Transactional
  public boolean checkNicknameAvailability(String nickname, String snsId) {
    if (memberMapper.existsByNickname(nickname)) {
      return false;
    }

    memberMapper.updatePendingMemberNickname(snsId, nickname);
    return true;
  }

  @Transactional
  public void registerMember(MemberSignupDto.Request memberSignupDto, String snsId) {
    this.ensureMemberExists(snsId);

    Member pendingMember = memberMapper.getMemberDetailBySnsId(snsId);

    Member activedMember = pendingMember.completeSignupProfile(memberSignupDto);
    memberMapper.updateActiveMember(activedMember);
  }

  private void ensureMemberExists(String snsId) {
    if (!memberMapper.existsMemberBySnsId(snsId)) {
      throw new IllegalArgumentException(
          "[NOT_FOUND] 회원 정보가 존재하지 않습니다. MEMBER_ID : " + snsId);
    }
  }
}

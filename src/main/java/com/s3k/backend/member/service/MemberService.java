package com.s3k.backend.member.service;

import com.s3k.backend.global.EncryptUtil;
import com.s3k.backend.member.dto.MemberDefaultDto;
import com.s3k.backend.member.dto.MemberSignupDto.Request;
import com.s3k.backend.member.entity.Member;
import com.s3k.backend.member.enums.Role;
import com.s3k.backend.member.enums.Sns;
import com.s3k.backend.member.enums.Status;
import com.s3k.backend.member.mapper.MemberMapper;
//import com.s3k.backend.member.service.inner.KakaoProvider;
//import com.s3k.backend.member.service.inner.MemberConverter;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class MemberService {

//  private final KakaoProvider kakaoProvider;
//  private final MemberConverter memberConverter;
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

//  public MemberDefaultDto.Response signin(MemberSigninDto.Request request) {
//
//    KakaoTokenDto tokenInfo = kakaoProvider.getTokenInfo(request.authCode());
//    // TODO : 예외처리 공통화 처리 후 수정.
//    if (tokenInfo == null) {
//      throw new RuntimeException("[UNAUTHORIZED] 소셜 로그인 실패. 관리자 문의 바람");
//    }
//    Long snsId = kakaoProvider.getSnsId(tokenInfo.getIdToken());
//    Member member = memberMapper.getMemberDetailBySnsId(snsId);
//    // TODO : 예외처리 공통화 처리 후 수정.
//    if (member == null) {
//      throw new RuntimeException(
//          "[UNAUTHORIZED] 가입된 회원이 아닙니다. SNS_ID : " + EncryptUtil.encodeId(snsId));
//    }
//    return memberConverter.convertResponse(member);
//  }

//  public MemberDefaultDto.Response signup(String sns, Request request) {

    // TODO : 회원 가입 전 Validation 추가.

//    LocalDateTime currentDateTime = LocalDateTime.now();
//    Member insertMember = new Member(
//        null,
//        request.snsId(),
//        Sns.valueOf(sns).getValue(),
//        request.nickname(),
//        request.tos(),
//        request.privacyPolicy(),
//        currentDateTime,
//        Role.MEMBER.getValue(),
//        Status.ACTIVE.getValue(),
//        currentDateTime,
//        currentDateTime
//    );
//    memberMapper.createMember(insertMember);
//    System.out.println(insertMember.getId());
//    Member newMember = memberMapper.getMemberDetail(insertMember.getId());
//    System.out.println(newMember);

//    return memberConverter.convertResponse(newMember);
//  }

  public Member getMemberDetailBySnsId(String snsId) {
    Member member = memberMapper.getMemberDetailBySnsId(snsId);

    if (ObjectUtils.isEmpty(member)) {
      throw new RuntimeException(
          "[NOT_FOUND] 회원 정보가 존재하지 않습니다. MEMBER_ID : " + EncryptUtil.encodeId(snsId));
    }

    return member;
  }
}

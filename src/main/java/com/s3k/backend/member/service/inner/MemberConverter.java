package com.s3k.backend.member.service.inner;

//import com.s3k.backend.member.dto.MemberDefaultDto;
//import com.s3k.backend.member.entity.Member;
//import com.s3k.backend.member.enums.Role;
//import com.s3k.backend.member.enums.Sns;
//import com.s3k.backend.member.enums.Status;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MemberConverter {
//
//  public MemberDefaultDto.Response convertResponse(Member member) {
//    return new MemberDefaultDto.Response(
//        member.getId(),
//        member.getSnsId(),
//        Sns.getEnum(member.getSns()),
//        member.getNickname(),
//        member.isTos(),
//        member.isPrivacyPolicy(),
//        member.getAgreeAt(),
//        Role.getEnum(member.getRole()),
//        Status.getEnum(member.getStatus()),
//        member.getCreatedAt(),
//        member.getUpdatedAt()
//    );
//  }
//}

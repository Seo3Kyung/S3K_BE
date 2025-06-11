package com.s3k.backend.member.mapper;

import com.s3k.backend.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

  boolean existsMemberBySnsId(String snsId);

  void insertPendingMember(Member pendingMember);

  void createMember(Member parameter);

  Member getMemberDetail(Long memberId);

  Member getMemberDetailBySnsId(String snsId);
}

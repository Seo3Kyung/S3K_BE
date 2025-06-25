package com.s3k.backend.member.mapper;

import com.s3k.backend.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

  boolean existsMemberBySnsId(String snsId);

  void insertPendingMember(Member pendingMember);

  void createMember(Member parameter);

  void updatePendingMemberProfile(@Param("snsId") String snsId, @Param("fileName") String fileName);

  Member getMemberDetail(Long memberId);

  Member getMemberDetailBySnsId(String snsId);
}

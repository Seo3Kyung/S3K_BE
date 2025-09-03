package com.s3k.backend.member.mapper;

import com.s3k.backend.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

  boolean existsMemberBySnsId(String snsId);

  boolean existsByNickname(String nickname);

  void insertPendingMember(Member pendingMember);

  void updatePendingMemberNickname(@Param("snsId") String snsId,
      @Param("nickname") String nickname);

  void updateActiveMember(Member activeMember);

  void updatePendingMemberProfile(@Param("snsId") String snsId, @Param("fileId") Long fileId);

  Member getMemberDetailBySnsId(String snsId);
}

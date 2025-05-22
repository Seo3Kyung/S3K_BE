package com.s3k.backend.member.mapper;

import com.s3k.backend.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

  void createMember(Member parameter);

  Member getMemberDetail(Long memberId);

  Member getMemberDetailBySnsId(Long snsId);
}

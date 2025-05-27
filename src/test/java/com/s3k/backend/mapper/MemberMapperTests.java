package com.s3k.backend.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.s3k.backend.member.entity.Member;
import com.s3k.backend.member.enums.Sns;
import com.s3k.backend.member.mapper.MemberMapper;
import com.s3k.backend.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@MapperScan(basePackages = "com.s3k.backend.member.mapper")
@Transactional
@Rollback
public class MemberMapperTests {

  @Autowired
  private MemberMapper memberMapper;
  @Autowired
  private MemberService memberService;

  @Test
  void contextLoads() {
    assertNotNull(memberMapper);
  }

  @Test
  @DisplayName("임시 회원가입 테스트")
  void testInsertPendingMember() {
    // given
    String snsId = "123456789";
    Sns sns = Sns.KAKAO;
    Member pendingMember = Member.createPendingMember(snsId, sns);

    // when
    memberMapper.insertPendingMember(pendingMember);

    // then
    Member member = memberMapper.getMemberDetailBySnsId(snsId);
    assertNotNull(pendingMember);
    assertEquals(snsId, member.getSnsId());
    assertEquals(sns.getValue(), member.getSns());
  }
}

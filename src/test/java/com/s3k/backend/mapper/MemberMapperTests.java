package com.s3k.backend.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.s3k.backend.member.entity.Member;
import com.s3k.backend.member.enums.Sns;
import com.s3k.backend.member.mapper.MemberMapper;
import java.time.LocalDateTime;
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

  @Test
  void contextLoads() {
    // 빈이 제대로 주입되는지 간단한 널 체크
    assertNotNull(memberMapper);
  }

  @Test
  @DisplayName("임시 회원가입 테스트")
  void testInsertPendingMember() {
    // given
    Long snsId = 123456789L;
    Sns sns = Sns.KAKAO;
    Member pendingMember = Member.createPendingMember(snsId, sns, LocalDateTime.now());

    // when
    memberMapper.insertPendingMember(pendingMember);

    // then
    Member insertMember = memberMapper.getMemberDetailBySnsId(snsId);
    assertNotNull(pendingMember);
    assertEquals(snsId, insertMember.getSnsId());
    assertEquals(sns.getValue(), insertMember.getSns());
  }
}

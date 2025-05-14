package com.s3k.backend.member.controller;


import com.s3k.backend.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@Slf4j
public class MemberController {

  @GetMapping("/test")
  public Member test() {
    log.error("디스코드 에러 테스트");
    return new Member("디스코드", 1);
  }

}

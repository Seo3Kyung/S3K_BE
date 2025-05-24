package com.s3k.backend.member.controller;


import com.s3k.backend.member.dto.MemberDefaultDto;
import com.s3k.backend.member.dto.MemberSigninDto;
import com.s3k.backend.member.dto.MemberSignupDto;
import com.s3k.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/test")
  public String test() {
//    log.error("디스코드 에러 테스트");
//    return new Member("디스코드", 1);
    return "에러";
  }

  @GetMapping("/authTest")
  public String authTest() {
    return "인증 테스트 성공";
  }

  @PostMapping("/sign-in")
  public MemberDefaultDto.Response signIn(
      @RequestBody MemberSigninDto.Request request
  ) {
    // TODO : 예외처리 공통화 처리 후 수정.
    return memberService.signin(request);
  }

  @PostMapping("/sign-up/{sns}")
  public MemberDefaultDto.Response signUp(
      @PathVariable String sns,
      @RequestBody MemberSignupDto.Request request
  ) {
    // TODO : 예외처리 공통화 처리 후 수정.
    return memberService.signup(sns.toUpperCase(), request);
  }
}

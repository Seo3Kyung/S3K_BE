package com.s3k.backend.member.controller;

import com.s3k.backend.global.dto.ApisResponse;
import com.s3k.backend.global.util.ValidatorUtils;
import com.s3k.backend.member.dto.MemberSignupDto;
import com.s3k.backend.member.entity.Member;
import com.s3k.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

  private static final String NICKNAME = "nickname";

  private final MemberService memberService;

  @GetMapping("/nickname/available")
  public ApisResponse<Boolean> checkNickname(@RequestParam("nickname") String nickname,
      @AuthenticationPrincipal Member principal) {
    ValidatorUtils.validate(NICKNAME, nickname);
    return ApisResponse.ok(memberService.checkNicknameAvailability(nickname, principal.getSnsId()));
  }

  @PostMapping("/signup")
  public ApisResponse<?> signUp(@RequestBody MemberSignupDto.Request request,
      @AuthenticationPrincipal Member principal) {
    memberService.registerMember(request, principal.getSnsId());
    return ApisResponse.ok();
  }
}

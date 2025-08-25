package com.s3k.backend.stopover.controller;

import com.s3k.backend.global.dto.ApisResponse;
import com.s3k.backend.member.entity.Member;
import com.s3k.backend.stopover.dto.CreateStopoverDto.Request;
import com.s3k.backend.stopover.dto.DeleteStopoverDto;
import com.s3k.backend.stopover.dto.GetStopoverDto;
import com.s3k.backend.stopover.dto.GetStopoversDto;
import com.s3k.backend.stopover.dto.UpdateStopoverDto;
import com.s3k.backend.stopover.service.StopoverService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stopover")
public class StopoverController {

  private final StopoverService stopoverService;

  @PostMapping("/create")
  public ApisResponse<?> createStopover(
      @AuthenticationPrincipal Member principal,
      @RequestBody Request request
  ) {
    return ApisResponse.ok(stopoverService.createStopover(request));
  }

  @PostMapping("/update")
  public ApisResponse<?> updateStopover(
      @AuthenticationPrincipal Member principal,
      @RequestBody UpdateStopoverDto.Request request
  ) {
    return ApisResponse.ok(stopoverService.updateStopover(request));
  }

  @DeleteMapping("/delete")
  public ApisResponse<?> deleteStopover(
      @AuthenticationPrincipal Member principal,
      @RequestBody DeleteStopoverDto.Request request
  ) {
    stopoverService.deleteStopover(request.stopoverId());
    return ApisResponse.ok("경유지 삭제 성공");
  }

  @PostMapping("/get-detail")
  public ApisResponse<?> getStopover(
      @AuthenticationPrincipal Member principal,
      @RequestBody GetStopoverDto.Request request
  ) {
    return ApisResponse.ok(stopoverService.getStopover(request.stopoverId()));
  }

  @PostMapping("/get-list")
  public ApisResponse<?> getStopovers(
      @AuthenticationPrincipal Member principal,
      @RequestBody GetStopoversDto.Request request
  ) {
    return ApisResponse.ok(stopoverService.getStopovers(request.walkingPathId()));
  }
}

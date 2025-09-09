package com.s3k.backend.walkingpath.controller;

import com.s3k.backend.global.dto.ApisResponse;
import com.s3k.backend.member.entity.Member;
import com.s3k.backend.walkingpath.dto.CreateWalkingpathDto;
import com.s3k.backend.walkingpath.dto.GetWalkingpathDetailDto;
import com.s3k.backend.walkingpath.dto.GetWalkingpathListDto;
import com.s3k.backend.walkingpath.dto.GetWalkingpathThemeListDto;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/walkingpath")
public class WalkingpathController {
  @PostMapping("/get-list")
  public ApisResponse<?> getWalkingpathList(
      @AuthenticationPrincipal Member principal,
      @RequestBody GetWalkingpathListDto.Request request
  ) {
    return ApisResponse.ok(List.of(
        new GetWalkingpathListDto.Response(
            1L,
            false,
            "url",
            "우리동네 커피 1등집이 있는 산책로",
            List.of("분위기 좋은", "항상 들르고 싶은"),
            1000L,
            "성수이로",
            "뚝섬 한강 공원"
        ),
        new GetWalkingpathListDto.Response(
            2L,
            true,
            "url",
            "우리동네 커피 1등집이 있는 산책로",
            List.of("분위기 좋은", "항상 들르고 싶은"),
            1000L,
            "성수이로",
            "뚝섬 한강 공원"
        ),
        new GetWalkingpathListDto.Response(
            3L,
            true,
            "url",
            "우리동네 커피 1등집이 있는 산책로",
            List.of("분위기 좋은", "항상 들르고 싶은"),
            1000L,
            "성수이로",
            "뚝섬 한강 공원"
        )
    ));
  }

  @PostMapping("/get-detail")
  public ApisResponse<?> getWalkingpathDetail() {
    return ApisResponse.ok(new GetWalkingpathDetailDto.Response(
        3L,
        "우리동네 커피 1등집이 있는 산책로",
        List.of("분위기 좋은", "항상 들르고 싶은"),
        "성수이로",
        "뚝섬 한강 공원",
        1000L,
        3,
        1000L,
        false,
        "대표 사진 URL",
        List.of("URL1", "URL2")
    ));
  }

  @PostMapping("/create")
  public ApisResponse<?> createWalkingpath(
      @AuthenticationPrincipal Member principal,
      @RequestBody CreateWalkingpathDto.Request request
  ) {
    return ApisResponse.ok(new GetWalkingpathDetailDto.Response(
        3L,
        "우리동네 커피 1등집이 있는 산책로",
        List.of("분위기 좋은", "항상 들르고 싶은"),
        "성수이로",
        "뚝섬 한강 공원",
        1000L,
        3,
        1000L,
        false,
        "대표 사진 URL",
        List.of("URL1", "URL2")
    ));
  }

  @PostMapping("/update")
  public ApisResponse<?> updateWalkingpath() {
    return ApisResponse.ok(new GetWalkingpathDetailDto.Response(
        3L,
        "우리동네 커피 1등집이 있는 산책로",
        List.of("분위기 좋은", "항상 들르고 싶은"),
        "성수이로",
        "뚝섬 한강 공원",
        1000L,
        3,
        1000L,
        false,
        "대표 사진 URL",
        List.of("URL1", "URL2")
    ));
  }

  @DeleteMapping("/delete")
  public ApisResponse<?> deleteWalkingpath() {
    return ApisResponse.ok("삭제 되었습니다.");
  }

  @GetMapping("/theme-list")
  public ApisResponse<?> getWalkingpathThemeList(
      @AuthenticationPrincipal Member principal
  ){
    return ApisResponse.ok(List.of(
        new GetWalkingpathThemeListDto.Response(
            1L,
            "반려동물"
        ),
        new GetWalkingpathThemeListDto.Response(
            2L,
            "커피와 함께"
        ),
        new GetWalkingpathThemeListDto.Response(
            3L,
            "자연 속에서"
        ),
        new GetWalkingpathThemeListDto.Response(
            4L,
            "운동이 되는"
        ),
        new GetWalkingpathThemeListDto.Response(
            5L,
            "강변 따라"
        )
    ));
  }
}
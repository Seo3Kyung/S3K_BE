package com.s3k.backend.stopover;

import com.s3k.backend.global.dto.ApisResponse;
import com.s3k.backend.member.entity.Member;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stopover")
public class StopoverController {

  @RequestMapping("/create")
  public ApisResponse<?> createStopover(
      @AuthenticationPrincipal Member principal,
      @RequestBody CreateStopoverDto.Request request
  ) {
    return ApisResponse.ok(
        new GetStopoverDto.Response(
            null,
            "경기도 시흥시 은행동 행복구",
            "분위기 좋은 카페 앞 화장실",
            StopoverType.REST_ROOM,
            List.of("URL1","URL2")
        )
    );
  }

  @RequestMapping("/update")
  public ApisResponse<?> updateStopover(
      @AuthenticationPrincipal Member principal
  ) {
    return ApisResponse.ok(
        new GetStopoverDto.Response(
            null,
            "경기도 시흥시 은행동 행복구",
            "분위기 좋은 카페 앞 화장실",
            StopoverType.REST_ROOM,
            List.of("URL1","URL2")
        )
    );
  }

  @RequestMapping("/delete")
  public ApisResponse<?> deleteStopover(
      @AuthenticationPrincipal Member principal
  ) {
    return ApisResponse.ok(
        "삭제 성공"
    );
  }

  @RequestMapping("/get-detail")
  public ApisResponse<?> getStopover(
      @AuthenticationPrincipal Member principal,
      @RequestBody GetStopoverDto.Request request
  ) {
    return ApisResponse.ok(
        new GetStopoverDto.Response(
            null,
            "경기도 시흥시 은행동 행복구",
            "분위기 좋은 카페 앞 화장실",
            StopoverType.REST_ROOM,
            List.of("URL1","URL2")
        )
    );
  }

  @RequestMapping("/get-list")
  public ApisResponse<?> getStopovers(
      @AuthenticationPrincipal Member principal
  ) {
    return ApisResponse.ok(
        List.of(
            new GetStopoverDto.Response(
                null,
                "경기도 시흥시 은행동 행복구",
                "분위기 좋은 카페 앞 화장실",
                StopoverType.REST_ROOM,
                List.of("URL1","URL2")
            ),
            new GetStopoverDto.Response(
                null,
                "경기도 시흥시 은행동 행복구",
                "분위기 좋은 카페",
                StopoverType.CAFE,
                List.of("URL1","URL2")
            ),
            new GetStopoverDto.Response(
                null,
                "경기도 시흥시 은행동 행복구",
                "분위기 좋은 카페 풍경",
                StopoverType.SIGHT,
                List.of("URL1","URL2")
            )
        )
    );
  }
}

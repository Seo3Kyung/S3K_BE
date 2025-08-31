package com.s3k.backend.home.controller;

import com.s3k.backend.global.dto.ApisResponse;
import com.s3k.backend.home.dto.CourseSummaryDto;
import com.s3k.backend.home.dto.NearByCoursesDto;
import com.s3k.backend.home.dto.RecentCoursesDto;
import com.s3k.backend.home.dto.WalkingPathDetailDto;
import com.s3k.backend.home.dto.WalkingPathSearchDto;
import com.s3k.backend.home.dto.WaypointDto;
import com.s3k.backend.home.service.HomeService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/homes")
@RequiredArgsConstructor
public class HomeController {

  private final HomeService homeService;

  // 추천 코스 조회
  @GetMapping("/recommendations")
  public ApisResponse<?> getRecommendedCourses(
      @Valid @ModelAttribute WalkingPathSearchDto walkingPathSearchDto) {
    // homeService.findRecommendedCourses();
    return ApisResponse.ok(List.of(
        new CourseSummaryDto.Response(
            1L,
            "한강 야경 산책 코스",
            "여의나루역",
            "반포대교 달빛무지개분수",
            "사용자 A",
            true,
            List.of("야경", "데이트", "산책"),
            5200L,
            4,
            95L
        ),
        new CourseSummaryDto.Response(
            2L,
            "북촌 한옥마을 탐방",
            "안국역",
            "창덕궁",
            "사용자 B",
            false,
            List.of("전통", "역사", "문화"),
            2800L,
            5,
            75L
        ),
        new CourseSummaryDto.Response(
            3L,
            "남산타워 오르기",
            "남산도서관",
            "N서울타워",
            "사용자 C",
            true,
            List.of("등산", "전망"),
            3700L,
            3,
            110L
        ),
        new CourseSummaryDto.Response(
            4L,
            "홍대 → 망원 한강공원 코스",
            "홍대입구역",
            "망원한강공원",
            "사용자 D",
            false,
            List.of("맛집", "산책", "강변"),
            4600L,
            4,
            85L
        )
    ));
  }

  // 최근 조회한 코스 조회
  @GetMapping("/recent")
  public ApisResponse<?> getRecentCourses() {
    // homeService.findRecentCourses();
    return ApisResponse.ok(List.of(
        new RecentCoursesDto.Response(
            1L,
            6600L,
            List.of("힐링", "데이트", "야경"),
            "성수동",
            "서울숲",
            LocalDate.now().minusDays(1)
        ),
        new RecentCoursesDto.Response(
            2L,
            12000L,
            List.of("러닝", "체력단련"),
            "잠실",
            "올림픽공원",
            LocalDate.now().minusDays(3)
        ),
        new RecentCoursesDto.Response(
            3L,
            3500L,
            List.of("맛집투어", "산책"),
            "망원동",
            "합정",
            LocalDate.now().minusDays(7)
        ),
        new RecentCoursesDto.Response(
            4L,
            8900L,
            List.of("가족", "아이와함께"),
            "홍대입구",
            "월드컵경기장",
            LocalDate.now().minusDays(10)
        ),
        new RecentCoursesDto.Response(
            5L,
            15400L,
            List.of("라이딩", "강변코스"),
            "여의도",
            "반포한강공원",
            LocalDate.now().minusDays(14)
        ),
        new RecentCoursesDto.Response(
            6L,
            4200L,
            List.of("카페투어", "데이트"),
            "익선동",
            "안국역",
            LocalDate.now()
        ),
        new RecentCoursesDto.Response(
            7L,
            21000L,
            List.of("하이킹", "풍경", "도보여행"),
            "북한산입구",
            "대동문",
            LocalDate.now().minusDays(20)
        )
    ));
  }

  // 위치 기반 코스 조회
  @GetMapping("/nearby")
  public ApisResponse<?> getNearbyCourses() {
    // homeService.findCoursesByLocation();
    return ApisResponse.ok(List.of(
        new NearByCoursesDto.Response(
            11L,
            2500L,
            35L,
            List.of("공원", "산책"),
            "서대문",
            "안산자락길"
        ),
        new NearByCoursesDto.Response(
            12L,
            7300L,
            110L,
            List.of("문화", "역사"),
            "광화문",
            "경복궁"
        ),
        new NearByCoursesDto.Response(
            13L,
            5200L,
            80L,
            List.of("전망", "야경"),
            "남산입구",
            "N서울타워"
        ),
        new NearByCoursesDto.Response(
            14L,
            11800L,
            180L,
            List.of("하천", "라이딩"),
            "뚝섬",
            "구리한강시민공원"
        ),
        new NearByCoursesDto.Response(
            15L,
            4100L,
            65L,
            List.of("시장투어", "맛집"),
            "광장시장",
            "종로3가"
        ),
        new NearByCoursesDto.Response(
            16L,
            6400L,
            95L,
            List.of("쇼핑", "데이트"),
            "강남역",
            "코엑스몰"
        ),
        new NearByCoursesDto.Response(
            17L,
            8900L,
            140L,
            List.of("바다", "힐링"),
            "송도",
            "인천대교 전망대"
        ),
        new NearByCoursesDto.Response(
            18L,
            13400L,
            210L,
            List.of("등산", "자연"),
            "불암산입구",
            "불암산정상"
        )
    ));
  }

  // 코스 상세 화면
  @GetMapping("/{walkingPathId}")
  public ApisResponse<WalkingPathDetailDto.Response> getCourseDetail(
      @PathVariable Long walkingPathId) {
    return ApisResponse.ok(homeService.findCourseDetail(walkingPathId));
//    return ApisResponse.ok(
//        new CourseDetailDto.Response(
//            "북촌 한옥마을 탐방 코스",
//            true,
//            "안국역",
//            "창덕궁",
//            3200L,
//            4,
//            80L,
//            List.of("전통", "역사", "문화"),
//            List.of(
//                new Stopover(1L, "북촌 한옥마을 입구", "전통 한옥이 줄지어 있는 골목 시작점", ""),
//                new Stopover(2L, "가회동 31번지", "사진 명소로 유명한 포토스팟", "서울 종로구 가회동 31"),
//                new Stopover(3L, "삼청동 카페거리", "걷다가 잠깐 쉬어가기 좋은 카페 밀집 지역", "서울 종로구 삼청로 일대"),
//                new Stopover(4L, "창덕궁", "세계문화유산으로 지정된 궁궐", "서울 종로구 율곡로 99")
//            )
//        )
//    );
  }

  // 다른 유저가 등록한 경유지
  @GetMapping("/{courseId}/waypoints")
  public ApisResponse<?> getCourseWaypoints(@PathVariable Long courseId) {
    // homeService.findWaypointsByCourseId();
    return ApisResponse.ok(List.of(
        new WaypointDto.Response(
            1L,
            "서울숲 메인광장",
            "서울 성동구 뚝섬로 273"
        ),
        new WaypointDto.Response(
            2L,
            "망원 한강공원 입구",
            "서울 마포구 망원동 482-6"
        ),
        new WaypointDto.Response(
            3L,
            "북촌 한옥마을",
            "서울 종로구 계동길 37"
        ),
        new WaypointDto.Response(
            4L,
            "DDP 디자인 플라자",
            "서울 중구 을지로7가 2-1"
        ),
        new WaypointDto.Response(
            5L,
            "롯데월드타워 전망대",
            "서울 송파구 올림픽로 300"
        ),
        new WaypointDto.Response(
            6L,
            "홍대 걷고싶은거리",
            "서울 마포구 어울마당로"
        )
    ));
  }

}

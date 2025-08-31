package com.s3k.backend.home.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.s3k.backend.home.entity.Stopover;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StopoverDtoTest {

  @Test
  @DisplayName("Stopover 엔티티 리스트로부터 Response DTO 리스트를 생성한다")
  void fromList_Success() {
    // given
    List<Stopover> stopoverList = Arrays.asList(
        Stopover.builder()
            .stopoverComment("맛있는 한식당")
            .stopoverCategory(1)
            .address("서울시 중구 명동길 123")
            .build(),
        Stopover.builder()
            .stopoverComment("아늑한 카페")
            .stopoverCategory(2)
            .address("서울시 종로구 인사동길 45")
            .build(),
        Stopover.builder()
            .stopoverComment("역사적인 궁궐")
            .stopoverCategory(3)
            .address("서울시 종로구 사직로 161")
            .build()
    );

    // when
    List<StopoverDto.Response> responses = StopoverDto.Response.fromList(stopoverList);

    // then
    assertThat(responses).hasSize(3);

    // 첫 번째 응답 검증
    StopoverDto.Response firstResponse = responses.get(0);
    assertThat(firstResponse.comment()).isEqualTo("맛있는 한식당");
    assertThat(firstResponse.stopoverCategory()).isEqualTo("식당");
    assertThat(firstResponse.address()).isEqualTo("서울시 중구 명동길 123");

    // 두 번째 응답 검증
    StopoverDto.Response secondResponse = responses.get(1);
    assertThat(secondResponse.comment()).isEqualTo("아늑한 카페");
    assertThat(secondResponse.stopoverCategory()).isEqualTo("카페");
    assertThat(secondResponse.address()).isEqualTo("서울시 종로구 인사동길 45");

    // 세 번째 응답 검증
    StopoverDto.Response thirdResponse = responses.get(2);
    assertThat(thirdResponse.comment()).isEqualTo("역사적인 궁궐");
    assertThat(thirdResponse.stopoverCategory()).isEqualTo("관광지");
    assertThat(thirdResponse.address()).isEqualTo("서울시 종로구 사직로 161");
  }

  @Test
  @DisplayName("빈 Stopover 리스트로부터 빈 Response DTO 리스트를 생성한다")
  void fromList_EmptyList() {
    List<Stopover> emptyStopoverList = List.of();

    List<StopoverDto.Response> responses = StopoverDto.Response.fromList(emptyStopoverList);

    assertThat(responses).isEmpty();
  }

  @Test
  @DisplayName("Builder를 사용하여 Response DTO를 생성한다")
  void builderPattern_Success() {
    StopoverDto.Response response = StopoverDto.Response.builder()
        .comment("멋진 공원")
        .stopoverCategory("공원")
        .address("서울시 용산구 용산동2가 8")
        .build();

    assertThat(response).isNotNull();
    assertThat(response.comment()).isEqualTo("멋진 공원");
    assertThat(response.stopoverCategory()).isEqualTo("공원");
    assertThat(response.address()).isEqualTo("서울시 용산구 용산동2가 8");
  }

  @Test
  @DisplayName("null 값들을 포함한 Stopover로 Response DTO를 생성하지만 category는 null 이면 없음이다")
  void fromList_WithNullValues() {
    // given
    List<Stopover> stopoverList = Collections.singletonList(
        Stopover.builder()
            .stopoverComment(null)
            .stopoverCategory(null)
            .address(null)
            .build()
    );

    // when
    List<StopoverDto.Response> responses = StopoverDto.Response.fromList(stopoverList);

    // then
    StopoverDto.Response response = responses.getFirst();
    assertThat(response.comment()).isNull();
    assertThat(response.stopoverCategory()).isEqualTo("없음");
    assertThat(response.address()).isNull();
  }
}

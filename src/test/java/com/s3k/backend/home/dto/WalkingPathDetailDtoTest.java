package com.s3k.backend.home.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.s3k.backend.home.entity.Stopover;
import com.s3k.backend.home.entity.WalkingPath;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WalkingPathDetailDtoTest {

  @Test
  @DisplayName("WalkingPath와 Stopover 리스트로 Response 객체 생성")
  void fromWalkingPathAndStopoverEntity_Success() {
    // given
    WalkingPath walkingPath = WalkingPath.builder()
        .walkingPathTitle("경복궁 둘레길")
        .walkingPathStartName("경복궁역")
        .walkingPathEndName("광화문역")
        .walkingDistance(2500L)
        .walkingTotalTime(30L)
        .build();

    List<Stopover> stopoverList = Arrays.asList(
        Stopover.builder()
            .stopoverComment("아름다운 궁궐")
            .stopoverCategory(0)
            .address("서울시 종로구 사직로 161")
            .build(),
        Stopover.builder()
            .stopoverComment("전통 찻집")
            .stopoverCategory(0)
            .address("서울시 종로구 인사동길 12")
            .build()
    );

    // when
    WalkingPathDetailDto.Response response =
        WalkingPathDetailDto.Response.fromWalkingPathAndStopoverEntity(walkingPath, stopoverList);

    // then
    assertThat(response).isNotNull();
    assertThat(response.title()).isEqualTo("경복궁 둘레길");
    assertThat(response.like()).isTrue();
    assertThat(response.startPointName()).isEqualTo("경복궁역");
    assertThat(response.endPointName()).isEqualTo("광화문역");
    assertThat(response.distance()).isEqualTo(2500L);
    assertThat(response.duration()).isEqualTo(30L);
    assertThat(response.walkingCategories()).containsExactly("전통", "역사", "문화");
    assertThat(response.stopover()).hasSize(2);
  }

  @Test
  @DisplayName("Stopover가 비어있어도 Response 생성 가능")
  void fromWalkingPathAndStopoverEntity_EmptyStopoverList() {
    // given
    WalkingPath walkingPath = WalkingPath.builder()
        .walkingPathTitle("한강 산책로")
        .walkingPathStartName("반포한강공원")
        .walkingPathEndName("잠원한강공원")
        .walkingDistance(3000L)
        .walkingTotalTime(45L)
        .build();

    List<Stopover> emptyStopoverList = List.of();

    // when
    WalkingPathDetailDto.Response response =
        WalkingPathDetailDto.Response.fromWalkingPathAndStopoverEntity(walkingPath,
            emptyStopoverList);

    // then
    assertThat(response).isNotNull();
    assertThat(response.title()).isEqualTo("한강 산책로");
    assertThat(response.stopover()).isEmpty();
  }
}

package com.s3k.backend.home.dto;

import com.s3k.backend.home.entity.Stopover;
import com.s3k.backend.home.entity.WalkingPath;
import java.util.List;
import lombok.Builder;

public class WalkingPathDetailDto {

  @Builder
  public record Response(
      String photoUrlPath,
      String title,
      boolean like,
      String startPointName,
      String endPointName,
      Long distance,
      Long duration,
      List<String> walkingCategories,
      List<StopoverDto.Response> stopover
  ) {

    public static Response fromWalkingPathAndStopoverEntity(WalkingPath walkingPath,
        List<Stopover> stopoverList, String photoUrlPath) {
      List<StopoverDto.Response> stopoverResponseList = StopoverDto.Response.fromList(stopoverList);

      return Response.builder()
          .photoUrlPath(photoUrlPath)
          .title(walkingPath.getWalkingPathTitle())
          .like(true)
          .startPointName(walkingPath.getWalkingPathStartName())
          .endPointName(walkingPath.getWalkingPathEndName())
          .distance(walkingPath.getWalkingDistance())
          .duration(walkingPath.getWalkingTotalTime())
          .walkingCategories(List.of("전통", "역사", "문화"))
          .stopover(stopoverResponseList)
          .build();
    }

  }

}

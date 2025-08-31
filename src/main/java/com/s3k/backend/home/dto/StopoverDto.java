package com.s3k.backend.home.dto;

import com.s3k.backend.home.entity.Stopover;
import com.s3k.backend.home.enums.StopoverCategory;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;

public class StopoverDto {

  @Builder
  public record Response(
      String comment,
      String stopoverCategory,
      String address
  ) {

    public static List<Response> fromList(List<Stopover> stopoverList) {
      List<Response> responses = new ArrayList<>();
      for (Stopover stopover : stopoverList) {
        String stopoverCategory = "없음";

        if (stopover.getStopoverCategory() != null) {
          stopoverCategory = StopoverCategory.getDescByValue(stopover.getStopoverCategory());
        }

        responses.add(
            StopoverDto.Response.builder()
                .comment(stopover.getStopoverComment())
                .stopoverCategory(stopoverCategory)
                .address(stopover.getAddress())
                .build()
        );

      }

      return responses;
    }

  }

}

package com.s3k.backend.stopover.dto.query;

import com.s3k.backend.stopover.dto.GetStopoverDto;
import com.s3k.backend.stopover.enums.StopoverType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetStopoverRtn {
  private Long stopoverId;
  private Long walkingPathId;
  private String address;
  private String comment;
  private Integer typeValue;
  private LocalDateTime createDatetime;
  private List<Long> imageIds = new ArrayList<>();

  public GetStopoverDto.Response mapToResponse(
      List<String> fileUrls
  ) {
    return new GetStopoverDto.Response(
        this.stopoverId,
        this.walkingPathId,
        this.address,
        this.comment,
        StopoverType.fromValue(this.typeValue),
        fileUrls,
        this.createDatetime
    );
  }
}

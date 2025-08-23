package com.s3k.backend.stopover;

import java.util.List;

public class CreateStopoverDto {

  public record Request(
      String address,
      String comment,
      StopoverType type,
      List<Long> imageIds
  ){}

  public record Response(

  ){}
}

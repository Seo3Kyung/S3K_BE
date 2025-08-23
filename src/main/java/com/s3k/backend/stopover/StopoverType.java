package com.s3k.backend.stopover;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StopoverType {

  PARK(1, "공원"),
  CAFE(2, "카페"),
  REST_ROOM(3, "화장실"),
  WORKOUT_THINGS(4, "운동기구"),
  SIGHT(5, "풍경"),
  ETC(6, "기타"),
  ;
  private final int value;
  private final String desc;

  public static StopoverType fromValue( int value ) {
    for( StopoverType stopoverType : values() ) {
      if( stopoverType.value == value ) return stopoverType;
    }
    return null;
  }
}

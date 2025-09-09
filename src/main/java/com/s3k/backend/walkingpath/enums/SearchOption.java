package com.s3k.backend.walkingpath.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchOption {

  RECOMMEND(1),
  RECENT_SEARCH(2),

  ;

  private final int value;
}

package com.s3k.backend.member.enums;

import java.util.EnumSet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
  UNKNOWN(0, "미정의"),
  ACTIVE(1, "사용"),
  DELETE(2, "탈퇴");
  private final int value;
  private final String desc;

  public static Status getEnum(int value) {
    EnumSet<Status> enumSet = EnumSet.allOf(Status.class);
    for (Status status : enumSet) {
      if (status.getValue() == value) {
        return status;
      }
    }
    return Status.UNKNOWN;
  }
}

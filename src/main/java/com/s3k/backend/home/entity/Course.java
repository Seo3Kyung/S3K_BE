package com.s3k.backend.home.entity;

import java.util.List;
import lombok.Getter;

@Getter
public class Course {

  private Long id;
  private String title;
  private boolean like;
  private String startLocation;
  private String endLocation;
  private Long distance;
  private List<String> stopoverList;
  private Long totalTime;


  public int getWaypointSize() {
    return this.stopoverList.size();
  }
}

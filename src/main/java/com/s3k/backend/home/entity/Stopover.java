package com.s3k.backend.home.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Stopover {

  private Long id;
  private String title;
  private String comment;
  private String address;

}

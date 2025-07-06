package com.s3k.backend.global.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

  public static String ofNowToStringDatetime(String pattern){
    LocalDateTime now = LocalDateTime.now();
    return now.format(DateTimeFormatter.ofPattern(pattern));
  }

  public static String ofNowToStringDate(String pattern){
    LocalDate now = LocalDate.now();
    return now.format(DateTimeFormatter.ofPattern(pattern));
  }
}

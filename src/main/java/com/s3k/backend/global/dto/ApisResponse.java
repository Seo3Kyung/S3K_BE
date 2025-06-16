package com.s3k.backend.global.dto;

import com.s3k.backend.global.enums.ApiResponseStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApisResponse<T>{
  private String code;
  private String message;
  private T data = null;
  private String responseTimeStamp;

  private ApisResponse(String code, String message, T data, String responseTimeStamp) {
    this.code = code;
    this.message = message;
    this.data = data;
    this.responseTimeStamp = responseTimeStamp;
  }

  public static ApisResponse<?> ok(){
    return new ApisResponse<>(
        ApiResponseStatus.OK.name(),
        ApiResponseStatus.OK.getDesc(),
        null,
        LocalDateTime.now().toString()
    );
  }

  public static <T> ApisResponse<T> ok(T data){
    return new ApisResponse<>(
        ApiResponseStatus.OK.name(),
        ApiResponseStatus.OK.getDesc(),
        data,
        LocalDateTime.now().toString()
    );
  }

  public static ApisResponse<String> error(ApiResponseStatus status) {
    return new ApisResponse<>(
        status.getCode(),
        status.getDesc(),
        null,
        LocalDateTime.now().toString()
    );
  }

  public static <T> ApisResponse<T> error(T cause, ApiResponseStatus status){
    return new ApisResponse<>(
        status.getCode(),
        status.getDesc(),
        cause,
        LocalDate.now().toString()
    );
  }
}
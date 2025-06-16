package com.s3k.backend.global;

import com.s3k.backend.global.dto.ApisResponse;
import com.s3k.backend.global.enums.ApiResponseStatus;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basic")
public class BasicController {

  @GetMapping("/health-check")
  public ApisResponse<?> healthCheck(){
    return ApisResponse.ok();
  }
  
  @GetMapping("/error-test1")
  public ApisResponse<?> errorTest1(){
    return ApisResponse.error(ApiResponseStatus.BAD_REQUEST);
  }

  @GetMapping("/error-test2")
  public ApisResponse<?> errorTest2(){
    return ApisResponse.error(ApiResponseStatus.MEMBER_VALIDATION);
  }
  
  @GetMapping("/error-test3")
  public ApisResponse<?> errorTest3(){
    return ApisResponse.error(new String[]{"1","2","3"}, ApiResponseStatus.INTERNAL_ERROR);
  }
}

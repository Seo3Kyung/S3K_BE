package com.s3k.backend.com;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basic")
public class BasicController {

  @GetMapping("/health-check")
  public String healthCheck(){
    return "OK";
  }
}

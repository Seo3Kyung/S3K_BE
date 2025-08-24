package com.s3k.backend.global;

import com.s3k.backend.global.address.service.AddressService;
import com.s3k.backend.global.dto.ApisResponse;
import com.s3k.backend.global.dto.address.GetNearbyRequest;
import com.s3k.backend.global.dto.address.GetNearbyResponse;
import com.s3k.backend.global.enums.ApiResponseStatus;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/basic")
public class BasicController {

  private final AddressService addressService;

  public BasicController(AddressService addressService) {
    this.addressService = addressService;
  }

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

  @PostMapping("/get-nearby")
  public ApisResponse<GetNearbyResponse> GetNearby(
      @RequestBody GetNearbyRequest request
  ) {
    List<String> results = addressService.getAddress(request);
    return ApisResponse.ok(new GetNearbyResponse(results));
  }
}

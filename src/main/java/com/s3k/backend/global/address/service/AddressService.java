package com.s3k.backend.global.address.service;

import static com.s3k.backend.global.enums.ApiResponseStatus.BAD_REQUEST;

import com.s3k.backend.global.address.dto.AddressInfo;
import com.s3k.backend.global.address.dto.KakaoAddressDto;
import com.s3k.backend.global.address.dto.inner.Address;
import com.s3k.backend.global.address.dto.inner.Document;
import com.s3k.backend.global.address.feign.KakaoFeign;
import com.s3k.backend.global.dto.address.GetNearbyRequest;
import com.s3k.backend.global.error.WalkiException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

  @Value("${kakao.client-id}")
  private String kakaoClientId;
  private final KakaoFeign kakaoFeign;

  public List<String> getAddress(
      GetNearbyRequest request
  ) {
    KakaoAddressDto dto1 = kakaoFeign.getAddressByCoordinate(
        "KakaoAK " + kakaoClientId,
        request.getLongitude().toString(),
        request.getLatitude().toString()
    );

    if(dto1 == null || dto1.getMeta() == null || dto1.getDocuments() == null) {
      throw new WalkiException(BAD_REQUEST);
    }

    String keyword = Arrays.stream(dto1.getDocuments())
        .map(x -> x.getAddress().getRegion1depthName() + " " +  x.getAddress().getRegion2depthName())
        .collect(Collectors.joining(" "));

    KakaoAddressDto dto2 = kakaoFeign.getAddressByKeyword(
        "KakaoAK " + kakaoClientId,
        keyword,
        request.getLongitude().toString(),
        request.getLatitude().toString(),
        20000
    );

    List<String> addressList = new ArrayList<>();
    Address address = Arrays.stream(dto1.getDocuments()).findFirst().get().getAddress();
    addressList.add(address.getRegion1depthName() + " " + address.getRegion2depthName() + " "
        + address.getRegion3depthName());
    addressList.addAll(Arrays.stream(dto2.getDocuments())
        .sorted(Comparator.comparing(Document::getDistance))
        .map(document ->
            document.getAddressName().substring(0, document.getAddressName().lastIndexOf("동") + 1)
        ).distinct().toList());
    return addressList;
  }
}

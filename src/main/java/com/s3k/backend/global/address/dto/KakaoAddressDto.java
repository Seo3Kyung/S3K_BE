package com.s3k.backend.global.address.dto;

import com.s3k.backend.global.address.dto.inner.Document;
import com.s3k.backend.global.address.dto.inner.Meta;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KakaoAddressDto {
  private Meta meta;
  private Document[] documents;
}

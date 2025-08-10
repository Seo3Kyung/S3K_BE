package com.s3k.backend.global.error;

import com.s3k.backend.global.dto.ApisResponse;

public interface ExceptionHandlerStrategy {

  boolean supports(Exception ex);

  ApisResponse<?> handle(Exception ex);
}

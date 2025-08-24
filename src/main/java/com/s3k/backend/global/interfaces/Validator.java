package com.s3k.backend.global.interfaces;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;

public interface Validator {
  void validate(MultipartFile file);

  default <T> void nullCheck(T data) {
    if (Objects.isNull(data)) {
      throw new RuntimeException("요청 데이터가 null이거나 비어 있습니다. 필수 입력 바랍니다.");
    } else if (data instanceof List || data instanceof Set) {
      if(((Collection<?>) data).isEmpty()) {
        throw new RuntimeException("요청 데이터가 비어 있습니다. 필수 입력 바랍니다.");
      }
    } else if (data instanceof String) {
      if(((String) data).isBlank()){
        throw new RuntimeException("요청 데이터가 빈 값입니다. 필수 입력 바랍니다.");
      }
    }
  }
}

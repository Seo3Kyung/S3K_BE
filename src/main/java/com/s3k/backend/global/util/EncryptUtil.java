package com.s3k.backend.global.util;

public class EncryptUtil {

  private static final Long SERIAL_KEY = 0x73656F336B79756EL; // seo3kyung 변환

  public static Long encodeId(String id) {
    return encodeId(Long.parseLong(id));
  }

  public static Long encodeId(Long id) {
    Long shiftedValue1 = (id % 128) << 56;
    Long shiftedValue2 = id >> 7;
    Long orValue = shiftedValue1 | shiftedValue2;
    return orValue ^ SERIAL_KEY;
  }

  public static Long decodeId(String id) {
    try {
      return decodeId(Long.parseLong(id));
    } catch (Exception e) {
      throw new RuntimeException("[ArithmeticException]");
    }
  }

  public static Long decodeId(Long id) {
    try {
      long xorValue = id ^ SERIAL_KEY;
      Long shiftedValue1 = (xorValue % 72057594037927936L) << 7;
      Long shiftedValue2 = xorValue >> 56;
      return shiftedValue1 | shiftedValue2;
    } catch (Exception e) {
      throw new RuntimeException("[ArithmeticException]");
    }
  }

}

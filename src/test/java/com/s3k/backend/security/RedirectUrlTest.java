package com.s3k.backend.security;

//import static org.mockito.Mockito.when;
//
//import com.s3k.backend.security.util.RedirectUrlUtils;
//import jakarta.servlet.http.HttpServletRequest;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;

//public class RedirectUrlTest {

//  private HttpServletRequest request;
//
//  @BeforeEach
//  void setUp() {
//    request = Mockito.mock(HttpServletRequest.class);
//  }
//
//  @Test
//  @DisplayName("기본 http://localhost:8080 형태 출력")
//  void 프론트_url_출력() {
//    when(request.getScheme()).thenReturn("http");
//    when(request.getServerName()).thenReturn("localhost");
//    when(request.getServerPort()).thenReturn(3000);
//
//    String result = RedirectUrlUtils.resolveFrontUrl(request);
//
//    Assertions.assertEquals("http://localhost:3000", result);
//  }
//}

package com.s3k.backend.security.util;

import jakarta.servlet.http.HttpServletRequest;

public final class RedirectUrlUtils {

  private RedirectUrlUtils() {
  }

  public static String resolveFrontUrl(HttpServletRequest request) {
    String scheme = request.getHeader("X-Forwarded-Proto");
    String host = request.getHeader("X-Forwarded-Host");
    String port = request.getHeader("X-Forwarded-Port");

    if (scheme == null || scheme.isEmpty()) {
      scheme = request.getScheme();
    }

    if (host == null || host.isEmpty()) {
      host = request.getServerName();
    }

    if (port == null || port.isEmpty()) {
      port = String.valueOf(request.getServerPort());
    }

    if (!isDefaultPort(scheme, port)) {
      port = ":" + port;
    }

    return scheme + "://" + host + ":3000";
  }

  private static boolean isDefaultPort(String scheme, String port) {
    return (scheme.equals("http") && "80".equals(port)) ||
        (scheme.equals("https") && "443".equals(port));
  }
}

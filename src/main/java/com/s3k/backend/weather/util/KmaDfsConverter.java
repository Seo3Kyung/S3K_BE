package com.s3k.backend.weather.util;

import com.s3k.backend.global.address.dto.KakaoAddressDto;
import com.s3k.backend.global.address.dto.inner.Document;

public class KmaDfsConverter {

  // 지구 반경(km)
  private static final double RE = 6371.00877;
  // 격자 간격(km)
  private static final double GRID = 5.0;
  // 표준위도1(deg)
  private static final double SLAT1 = 30.0;
  // 표준위도2(deg)
  private static final double SLAT2 = 60.0;
  // 기준경도(deg)
  private static final double OLON = 126.0;
  // 기준위도(deg)
  private static final double OLAT = 38.0;
  // 기준 X좌표
  private static final double XO = 43;
  // 기준 Y좌표
  private static final double YO = 136;

  public static class Point {

    public final int nowx;
    public final int nowy;

    public Point(int nowx, int nowy) {
      this.nowx = nowx;
      this.nowy = nowy;
    }
  }

  public static Point getDfsPoint(KakaoAddressDto kakaoAddressDto) {
    Document[] documents = kakaoAddressDto.getDocuments();
    Document document = documents[0];

    double longitude = Double.parseDouble(document.getX());
    double latitude = Double.parseDouble(document.getY());

    return toGrid(latitude, longitude);
  }

  private static Point toGrid(double lat, double lon) {
    double DEGRAD = Math.PI / 180.0;
    double re = RE / GRID;
    double slat1 = SLAT1 * DEGRAD;
    double slat2 = SLAT2 * DEGRAD;
    double olon = OLON * DEGRAD;
    double olat = OLAT * DEGRAD;

    double sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) /
        Math.log(Math.tan(Math.PI * 0.25 + slat2 * 0.5) /
            Math.tan(Math.PI * 0.25 + slat1 * 0.5));
    double sf = Math.pow(Math.tan(Math.PI * 0.25 + slat1 * 0.5), sn) *
        (Math.cos(slat1) / sn);
    double ro = re * sf /
        Math.pow(Math.tan(Math.PI * 0.25 + olat * 0.5), sn);

    double ra = re * sf /
        Math.pow(Math.tan(Math.PI * 0.25 + lat * DEGRAD * 0.5), sn);

    double theta = (lon * DEGRAD) - olon;

    if (theta > Math.PI) {
      theta -= 2.0 * Math.PI;
    }

    if (theta < -Math.PI) {
      theta += 2.0 * Math.PI;
    }
    theta *= sn;

    int x = (int) Math.floor(ra * Math.sin(theta) + XO + 0.5);
    int y = (int) Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);

    return new Point(x, y);
  }
}

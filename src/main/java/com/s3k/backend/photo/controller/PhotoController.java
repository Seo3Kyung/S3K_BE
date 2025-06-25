package com.s3k.backend.photo.controller;

import com.s3k.backend.global.dto.ApisResponse;
import com.s3k.backend.member.entity.Member;
import com.s3k.backend.photo.service.PhotoService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/photos")
@RequiredArgsConstructor
public class PhotoController {

  private final PhotoService photoService;

  @PostMapping("/register")
  public ApisResponse<String> saveProfileImageForRegister(
      @RequestParam("file") MultipartFile file,
      @AuthenticationPrincipal Member principal) throws IOException {
    return ApisResponse.ok(photoService.savePhotoForRegister(file, principal.getSnsId()));
  }

  @PostMapping("/s3/save")
  public ApisResponse<String> saveProfileImageForSave(
      @RequestPart MultipartFile file,
      @AuthenticationPrincipal Member principal) throws IOException {
    return ApisResponse.ok(photoService.savePhotoInS3(file, principal.getSnsId()));
  }
}
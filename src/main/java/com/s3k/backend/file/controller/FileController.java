package com.s3k.backend.file.controller;

import com.s3k.backend.file.service.FileService;
import com.s3k.backend.file.validator.ImageValidator;
import com.s3k.backend.global.dto.ApisResponse;
import com.s3k.backend.member.entity.Member;
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
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

  private final FileService fileService;
  private final ImageValidator imageValidator;

  @PostMapping("/register")
  public ApisResponse<?> saveProfileImageForRegister(
      @RequestParam("file") MultipartFile file,
      @AuthenticationPrincipal Member principal) {
    imageValidator.validate(file);
    String snsId = principal.getSnsId(); // JWT 필요
//    fileService.saveProfileForRegister(file, "111111111122");
    fileService.saveProfileForRegister(file, snsId);
    return ApisResponse.ok();
  }

  @PostMapping("/s3/save")
  public ApisResponse<?> saveProfileImageForSave(
      @AuthenticationPrincipal Member principal) {
    String snsId = principal.getSnsId(); // JWT 필요
//    String tmpSnsId = "111111111122";
    return ApisResponse.ok(fileService.saveProfileForS3(snsId));
  }
}
package com.s3k.backend.stopover.service;

import com.s3k.backend.file.entity.FileEntity;
import com.s3k.backend.file.enums.FileStatus;
import com.s3k.backend.file.mapper.FileMapper;
import com.s3k.backend.global.enums.ApiResponseStatus;
import com.s3k.backend.global.error.WalkiException;
import com.s3k.backend.stopover.dto.CreateStopoverDto;
import com.s3k.backend.stopover.dto.GetStopoverDto;
import com.s3k.backend.stopover.dto.UpdateStopoverDto;
import com.s3k.backend.stopover.dto.query.DeleteStopover;
import com.s3k.backend.stopover.dto.query.GetStopoverRtn;
import com.s3k.backend.stopover.dto.query.InsertStopover;
import com.s3k.backend.stopover.dto.query.InsertStopoverFile;
import com.s3k.backend.stopover.dto.query.UpdateStopover;
import com.s3k.backend.stopover.mapper.StopoverMapper;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StopoverService {

  private final StopoverMapper stopoverMapper;
  private final FileMapper fileMapper;

  @Transactional
  public GetStopoverDto.Response createStopover(
      CreateStopoverDto.Request request
  ) {
    InsertStopover insertDto = InsertStopover.builder()
        .walkingPathId(request.walkingPathId())
        .address(request.address())
        .comment(request.comment())
        .typeValue(request.type().getValue())
        .imageIds(request.imageIds())
        .build();
    stopoverMapper.insertStopover(insertDto);

    List<InsertStopoverFile> insertDtoList = request.imageIds().stream()
        .map(fileId ->
          InsertStopoverFile.builder()
              .imageId(fileId)
              .stopoverId(insertDto.getStopoverId())
              .isRepresentative(Objects.equals(fileId, request.representativeImageId()))
              .build())
        .toList();
    stopoverMapper.insertStopoverFile(insertDtoList);

    return getStopover(insertDto.getStopoverId());
  }

  public GetStopoverDto.Response updateStopover(
      UpdateStopoverDto.Request request
  ) {
    UpdateStopover updateDto = UpdateStopover.builder()
        .stopoverId(request.stopoverId())
        .address(request.address())
        .comment(request.comment())
        .typeValue(request.type().getValue())
        .imageIds(request.imageIds())
        .build();
    stopoverMapper.updateStopover(updateDto);

    // TODO : 경유지 파일 업로드 관련 로직 고민하기.
    request.imageIds();

    return getStopover(updateDto.getStopoverId());
  }

  public void deleteStopover(Long stopoverId) {
    stopoverMapper.deleteStopover(
        DeleteStopover.builder()
            .stopoverId(stopoverId)
            .deleteDatetime(LocalDateTime.now())
            .build()
    );
    stopoverMapper.getStopoverFiles(stopoverId).forEach(stopoverFile ->
      fileMapper.deleteFile(stopoverFile, FileStatus.DELETE.getValue(), LocalDateTime.now())
    );
  }

  public GetStopoverDto.Response getStopover(
      Long stopoverId
  ) {
    GetStopoverRtn getRtn = stopoverMapper.getStopover(stopoverId)
        .orElseThrow(() -> new WalkiException(ApiResponseStatus.STOPOVER_NOT_FOUND));

    List<String> imageUrls = fileMapper.getFiles(getRtn.getImageIds())
        .orElseGet(Collections::emptyList).stream()
        .map(FileEntity::getFilePath)
        .toList();

    return getRtn.mapToResponse(imageUrls);
  }

  public List<GetStopoverDto.Response> getStopovers(
      Long walkingPathId
  ) {
    return stopoverMapper.getStopovers(walkingPathId).stream()
        .map(stopover -> stopover.mapToResponse(Collections.emptyList()))
        .toList();
  }
}

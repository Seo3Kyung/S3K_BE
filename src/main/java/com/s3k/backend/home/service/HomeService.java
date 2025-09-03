package com.s3k.backend.home.service;

import com.s3k.backend.global.enums.ApiResponseStatus;
import com.s3k.backend.global.error.WalkiException;
import com.s3k.backend.home.dto.WalkingPathDetailDto;
import com.s3k.backend.home.entity.Stopover;
import com.s3k.backend.home.entity.WalkingPath;
import com.s3k.backend.home.mapper.HomeMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeService {

  private final HomeMapper homeMapper;

  public void findRecommendedCourses() {
  }

  public void findRecentCourses() {
  }

  public void findCoursesByLocation() {
  }

  public WalkingPathDetailDto.Response findCourseDetail(Long walkingPathId) {
    if (!homeMapper.existsByWalkingPathId(walkingPathId)) {
      throw new WalkiException(ApiResponseStatus.NOT_FOUND_WALKING_PATH);
    }

    WalkingPath walkingPathEntity = homeMapper.findWalkingPathById(walkingPathId);
    List<Stopover> stopoverEntityList = homeMapper.findAllStopoverByWalkingPathId(walkingPathId);

    String photoUrlPath = null;
    if (walkingPathEntity.isImageExposure()) {
      photoUrlPath = homeMapper.findRepresentativePhoto(stopoverEntityList);
    }

    return WalkingPathDetailDto.Response.fromWalkingPathAndStopoverEntity(walkingPathEntity,
        stopoverEntityList, photoUrlPath);
  }

  public void findWaypointsByCourseId() {
  }
}

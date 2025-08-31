package com.s3k.backend.home.service;

import com.s3k.backend.global.enums.ApiResponseStatus;
import com.s3k.backend.global.error.WalkiException;
import com.s3k.backend.home.dto.WalkingPathDetailDto;
import com.s3k.backend.home.entity.Stopover;
import com.s3k.backend.home.entity.WalkingPath;
import com.s3k.backend.home.mapper.StopoverMapper;
import com.s3k.backend.home.mapper.WalkingPathMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeService {

  private final WalkingPathMapper walkingPathMapper;
  private final StopoverMapper stopoverMapper;

  public void findRecommendedCourses() {
  }

  public void findRecentCourses() {
  }

  public void findCoursesByLocation() {
  }

  public WalkingPathDetailDto.Response findCourseDetail(Long walkingPathId) {
    if (!walkingPathMapper.existsByWalkingPathId(walkingPathId)) {
      throw new WalkiException(ApiResponseStatus.NOT_FOUND_WALKING_PATH);
    }

    // Entity 가져오기
    WalkingPath walkingPathEntity = walkingPathMapper.findWalkingPathById(walkingPathId);
    List<Stopover> stopoverList = stopoverMapper.findAllByWalkingPathId(walkingPathId);

    return WalkingPathDetailDto.Response.fromWalkingPathAndStopoverEntity(walkingPathEntity,
        stopoverList);
  }

  public void findWaypointsByCourseId() {
  }
}

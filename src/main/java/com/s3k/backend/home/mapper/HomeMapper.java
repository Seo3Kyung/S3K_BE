package com.s3k.backend.home.mapper;

import com.s3k.backend.home.entity.Stopover;
import com.s3k.backend.home.entity.WalkingPath;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HomeMapper {

  WalkingPath findWalkingPathById(Long walkingPathId);

  boolean existsByWalkingPathId(Long walkingPathId);

  List<Stopover> findAllStopoverByWalkingPathId(Long walkingPathId);

  String findRepresentativePhoto(List<Stopover> stopoverList);
}

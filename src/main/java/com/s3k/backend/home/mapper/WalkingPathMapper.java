package com.s3k.backend.home.mapper;

import com.s3k.backend.home.entity.WalkingPath;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WalkingPathMapper {

  WalkingPath findWalkingPathById(Long walkingPathId);

  boolean existsByWalkingPathId(Long walkingPathId);
}

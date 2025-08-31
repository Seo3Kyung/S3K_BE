package com.s3k.backend.home.mapper;

import com.s3k.backend.home.entity.Stopover;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StopoverMapper {

  List<Stopover> findAllByWalkingPathId(Long walkingPathId);
}

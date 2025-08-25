package com.s3k.backend.stopover.mapper;

import com.s3k.backend.stopover.dto.query.DeleteStopover;
import com.s3k.backend.stopover.dto.query.GetStopoverRtn;
import com.s3k.backend.stopover.dto.query.InsertStopover;
import com.s3k.backend.stopover.dto.query.InsertStopoverFile;
import com.s3k.backend.stopover.dto.query.UpdateStopover;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StopoverMapper {
  void insertStopover(InsertStopover insertDto);
  Optional<GetStopoverRtn> getStopover(Long stopoverId);
  void updateStopover(UpdateStopover updateDto);
  void deleteStopover(DeleteStopover deleteDto);
  List<GetStopoverRtn> getStopovers(Long walkingPathId);

  void insertStopoverFile(List<InsertStopoverFile> insertDtoList);
  List<Long> getStopoverFiles(Long stopoverId);
}

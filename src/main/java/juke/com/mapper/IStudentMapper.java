package juke.com.mapper;

import java.util.List;
import juke.com.dto.StudentDto;
import juke.com.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IStudentMapper extends IBaseMapper<StudentEntity, StudentDto> {

  @Mapping(target = "bookSet", source = "bookSet")
  @Override
  StudentDto toDto(StudentEntity entity);

  @Mapping(target = "bookSet", source = "bookSet")
  @Override
  StudentEntity toEntity(StudentDto dto);

  @Override
  List<StudentDto> toDtoList(List<StudentEntity> entityList);

  @Override
  List<StudentEntity> toEntityList(List<StudentDto> dtoList);
}
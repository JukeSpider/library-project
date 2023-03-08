package juke.com.mapper;

import java.util.List;
import juke.com.dto.BookDto;
import juke.com.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IBookMapper extends IBaseMapper<BookEntity, BookDto> {

  @Mapping(target = "author", source = "author")
  @Mapping(target = "genre", source = "genre")
  @Override
  BookDto toDto(BookEntity entity);

  @Mapping(target = "author", source = "author")
  @Mapping(target = "genre", source = "genre")
  @Override
  BookEntity toEntity(BookDto dto);

  @Override
  List<BookDto> toDtoList(List<BookEntity> entityList);

  @Override
  List<BookEntity> toEntityList(List<BookDto> dtoList);
}
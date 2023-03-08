package juke.com.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

public interface IBaseMapper<E, D> {

  D toDto(E entity);

  E toEntity(D dto);

  List<D> toDtoList(List<E> entityList);

  List<E> toEntityList(List<D> dtoList);
}
package juke.com.dto.base;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public abstract class LongIdDto {

  private Long id;

  @Override
  public boolean equals(Object o) {

    if (this == o) {
      return true;
    }

    if (!(o instanceof LongIdDto longIdDto)) {
      return false;
    }

    return id.equals(longIdDto.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
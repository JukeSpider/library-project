package juke.com.dto.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public abstract class BaseDto extends LongIdDto {

  private String createdAt;

  private String modifiedAt;

  protected BaseDto(Long id, String createdAt, String modifiedAt) {
    super(id);
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
  }
}
package juke.com.dto;

import java.util.Set;
import juke.com.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StudentDto extends BaseDto {

  private String name;

  private Integer age;

  private String phone;

  private String gender;

  private Set<BookDto> bookSet;

  public StudentDto(
      Long id,
      String createdAt,
      String modifiedAt,
      String name,
      Integer age,
      String phone,
      String gender,
      Set<BookDto> bookSet
  ) {
    super(id, createdAt, modifiedAt);
    this.name = name;
    this.age = age;
    this.phone = phone;
    this.gender = gender;
    this.bookSet = bookSet;
  }
}
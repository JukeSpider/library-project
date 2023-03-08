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
public class BookDto extends BaseDto {

  private String name;

  private String series;

  private Set<String> author;

  private Integer publicationYear;

  private Integer quantity;

  private Set<String> genre;

  public BookDto(
      Long id,
      String createdAt,
      String modifiedAt,
      String name,
      String series,
      Set<String> author,
      Integer publicationYear,
      Integer quantity,
      Set<String> genre
  ) {
    super(id, createdAt, modifiedAt);
    this.name = name;
    this.series = series;
    this.author = author;
    this.publicationYear = publicationYear;
    this.quantity = quantity;
    this.genre = genre;
  }
}
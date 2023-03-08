package juke.com.entity;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import juke.com.entity.base.BaseEntity;
import juke.com.entity.enums.GenreEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "books")
@Entity
public class BookEntity extends BaseEntity {

  @Column(name = "name")
  private String name;

  @Column(name = "series")
  private String series;

  @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "authors", joinColumns = @JoinColumn(name = "book_id"))
  private Set<String> author;

  @Column(name = "year_of_publication")
  private Integer publicationYear;

  @Column(name = "quantity")
  private Integer quantity;

  @Enumerated(value = EnumType.STRING)
  @ElementCollection(targetClass = GenreEnum.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "genres", joinColumns = @JoinColumn(name = "book_id"))
  private Set<GenreEnum> genre;

  public BookEntity(
      Long id,
      LocalDateTime createdAt,
      LocalDateTime modifiedAt,
      String name,
      String series,
      Set<String> author,
      Integer publicationYear,
      Integer quantity,
      Set<GenreEnum> genre
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
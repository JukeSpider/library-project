package juke.com.entity;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import juke.com.entity.base.BaseEntity;
import juke.com.entity.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "students")
@Entity
public class StudentEntity extends BaseEntity {

  @Column(name = "name")
  private String name;

  @Column(name = "age")
  private Integer age;

  @Column(name = "phone")
  private String phone;

  @Column(name = "gender")
  @Enumerated(value = EnumType.STRING)
  private GenderEnum gender;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "students_books",
      joinColumns = @JoinColumn(name = "student_id"),
      inverseJoinColumns = @JoinColumn(name = "book_id")
  )
  private Set<BookEntity> bookSet;

  public StudentEntity(
      Long id,
      LocalDateTime createdAt,
      LocalDateTime modifiedAt,
      String name,
      Integer age,
      String phone,
      GenderEnum gender,
      Set<BookEntity> bookSet
  ) {
    super(id, createdAt, modifiedAt);
    this.name = name;
    this.age = age;
    this.phone = phone;
    this.gender = gender;
    this.bookSet = bookSet;
  }
}
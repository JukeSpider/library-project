package juke.com.repository;

import static juke.com.entity.enums.GenderEnum.FEMALE;
import static juke.com.entity.enums.GenderEnum.MALE;
import static juke.com.entity.enums.GenderEnum.OTHER;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import juke.com.entity.BookEntity;
import juke.com.entity.StudentEntity;
import juke.com.entity.enums.GenderEnum;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@TestInstance(value = Lifecycle.PER_CLASS)
@DataJpaTest
class StudentRepositoryTest {

  private final IStudentRepository studentRepo;
  private final IBookRepository bookRepo;

  private final Pageable pageable = PageRequest.of(0, 100);

  @Autowired
  StudentRepositoryTest(IStudentRepository studentRepo, IBookRepository bookRepo) {
    this.studentRepo = studentRepo;
    this.bookRepo = bookRepo;
  }

  @BeforeAll
  void beforeAll() {

    File studentFile1 = new File("src/test/resources/repoTest/student/json/student1.json");
    File studentFile2 = new File("src/test/resources/repoTest/student/json/student2.json");
    File studentFile3 = new File("src/test/resources/repoTest/student/json/student3.json");
    File studentFile4 = new File("src/test/resources/repoTest/student/json/student4.json");
    File studentFile5 = new File("src/test/resources/repoTest/student/json/student5.json");
    File studentFile6 = new File("src/test/resources/repoTest/student/json/student6.json");

    File bookSetFile1 = new File("src/test/resources/repoTest/book/json/book_set_1.json");
    File bookSetFile2 = new File("src/test/resources/repoTest/book/json/book_set_2.json");
    File bookSetFile3 = new File("src/test/resources/repoTest/book/json/book_set_3.json");

    StudentEntity student1;
    StudentEntity student2;
    StudentEntity student3;
    StudentEntity student4;
    StudentEntity student5;
    StudentEntity student6;

    Set<BookEntity> bookSet1;
    Set<BookEntity> bookSet2;
    Set<BookEntity> bookSet3;

    try {
      student1 = new ObjectMapper().readValue(studentFile1, new TypeReference<>() {});
      student2 = new ObjectMapper().readValue(studentFile2, new TypeReference<>() {});
      student3 = new ObjectMapper().readValue(studentFile3, new TypeReference<>() {});
      student4 = new ObjectMapper().readValue(studentFile4, new TypeReference<>() {});
      student5 = new ObjectMapper().readValue(studentFile5, new TypeReference<>() {});
      student6 = new ObjectMapper().readValue(studentFile6, new TypeReference<>() {});

      bookSet1 = new ObjectMapper().readValue(bookSetFile1, new TypeReference<>() {});
      bookSet2 = new ObjectMapper().readValue(bookSetFile2, new TypeReference<>() {});
      bookSet3 = new ObjectMapper().readValue(bookSetFile3, new TypeReference<>() {});
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    bookRepo.saveAll(bookSet1);
    bookRepo.saveAll(bookSet2);
    bookRepo.saveAll(bookSet3);

    student1.setBookSet(bookSet1);
    student2.setBookSet(bookSet2);
    student3.setBookSet(bookSet3);
    student4.setBookSet(bookSet1);
    student5.setBookSet(bookSet2);
    student6.setBookSet(bookSet3);

    studentRepo.save(student1);
    studentRepo.save(student2);
    studentRepo.save(student3);
    studentRepo.save(student4);
    studentRepo.save(student5);
    studentRepo.save(student6);
  }


  @DisplayName("Should find all student by gender")
  @Test
  void shouldFindAllByGender() {
    // given
    GenderEnum studentGender = MALE;
    Page<StudentEntity> studentPage = studentRepo.findAllByGender(studentGender, pageable);
    List<StudentEntity> studentList = studentPage.getContent();
    // then
    assertThat(studentList).hasSize(3)
        .extracting(StudentEntity::getGender)
        .allMatch(gender -> gender.equals(studentGender));
  }

  @DisplayName("Should find all student by age")
  @Test
  void shouldFindAllByAge() {
    // given
    Integer studentAge = 20;
    Page<StudentEntity> studentPage = studentRepo.findAllByAge(studentAge, pageable);
    List<StudentEntity> studentList = studentPage.getContent();
    // then
    assertThat(studentList).hasSize(2)
        .extracting(StudentEntity::getAge)
        .allMatch(age -> age.equals(studentAge));
  }

  @DisplayName("Should find all student by book's id")
  @Test
  void shouldFindAllByBookId() {
    // given
    Long bookId = 2L;
    Page<StudentEntity> studentPage = studentRepo.findAllByBookId(bookId, pageable);
    List<StudentEntity> studentList = studentPage.getContent();
    // then
    assertThat(studentList).hasSize(2)
        .extracting(StudentEntity::getBookSet)
        .allMatch(
            bookSet -> bookSet.stream()
                .map(BookEntity::getId)
                .anyMatch(id -> id.equals(bookId))
        );
  }

  @DisplayName("Should find student by phone")
  @Test
  void shouldFindByPhone() {
    // given
    String studentName = "Yuli";
    Integer studentAge = 25;
    String studentPhone = "+7(989)111-41-88";

    Optional<StudentEntity> studentOptional = studentRepo.findByPhone(studentPhone);
    // then
    assertThat(studentOptional).isPresent()
        .hasValueSatisfying(student -> {
          assertThat(student.getName()).isEqualTo(studentName);
          assertThat(student.getAge()).isEqualTo(studentAge);
          assertThat(student.getPhone()).isEqualTo(studentPhone);
          assertThat(student.getGender()).isEqualTo(OTHER);
        });
  }

  @DisplayName("Should find student by name")
  @Test
  void shouldFindByName() {
    // given
    String studentName = "Violet";
    Integer studentAge = 21;
    String studentPhone = "+7(614)485-10-70";

    Optional<StudentEntity> studentOptional = studentRepo.findByName(studentName);
    // then
    assertThat(studentOptional).isPresent()
        .hasValueSatisfying(student -> {
          assertThat(student.getName()).isEqualTo(studentName);
          assertThat(student.getAge()).isEqualTo(studentAge);
          assertThat(student.getPhone()).isEqualTo(studentPhone);
          assertThat(student.getGender()).isEqualTo(FEMALE);
        });
  }

  @DisplayName("Should return true if book exist by name")
  @Test
  void shouldReturnTrueIfStudentExistByName() {
    // given
    String name = "Elena";
    boolean isExist = studentRepo.existsByName(name);
    // then
    assertThat(isExist).isTrue();
  }

  @DisplayName("Should return false if book does not exist by name")
  @Test
  void shouldReturnFalseIfStudentDoesNotExistByName() {
    // given
    String name = "SomeRandomName";
    boolean isExist = studentRepo.existsByName(name);
    // then
    assertThat(isExist).isFalse();
  }

  @DisplayName("Should return true if book exist by phone")
  @Test
  void shouldReturnTrueIfStudentExistByPhone() {
    // given
    String phone = "+7(383)125-11-99";
    boolean isExist = studentRepo.existsByPhone(phone);
    // then
    assertThat(isExist).isTrue();
  }

  @DisplayName("Should return false if book does not exist by phone")
  @Test
  void shouldReturnFalseIfStudentDoesNotExistByPhone() {
    // given
    String phone = "+7(000)000-00-00";
    boolean isExist = studentRepo.existsByName(phone);
    // then
    assertThat(isExist).isFalse();
  }

  @DisplayName("Should delete student by name")
  @Test
  void shouldDeleteByName() {
    // given
    String name = "Elena";
    Optional<StudentEntity> deleted = studentRepo.findByName(name);
    // then
    assertThat(deleted).isPresent();
    // when
    studentRepo.deleteByName(name);
    //then
    assertThat(studentRepo.existsByName(name)).isFalse();

    studentRepo.save(deleted.get());
  }

  @DisplayName("Should delete student by phone")
  @Test
  void shouldDeleteByPhone() {
    // given
    String phone = "+7(383)125-11-99";
    Optional<StudentEntity> deleted = studentRepo.findByPhone(phone);
    // then
    assertThat(deleted).isPresent();
    // when
    studentRepo.deleteByPhone(phone);
    //then
    assertThat(studentRepo.existsByName(phone)).isFalse();

    studentRepo.save(deleted.get());
  }
}
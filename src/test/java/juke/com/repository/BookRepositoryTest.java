package juke.com.repository;

import static juke.com.entity.enums.GenreEnum.ADVENTURES;
import static juke.com.entity.enums.GenreEnum.DETECTIVE;
import static juke.com.entity.enums.GenreEnum.FANTASY;
import static juke.com.entity.enums.GenreEnum.FOREIGN;
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
import juke.com.entity.enums.GenreEnum;
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
class BookRepositoryTest {

  private final IBookRepository bookRepo;
  private final IStudentRepository studentRepo;
  private final Pageable pageable = PageRequest.of(0, 100);

  @Autowired
  public BookRepositoryTest(IBookRepository bookRepo, IStudentRepository studentRepo) {
    this.bookRepo = bookRepo;
    this.studentRepo = studentRepo;
  }

  @BeforeAll
  void beforeAll() {

    File studentFile2 = new File("src/test/resources/repoTest/student/json/student2.json");
    File studentFile1 = new File("src/test/resources/repoTest/student/json/student1.json");
    File studentFile3 = new File("src/test/resources/repoTest/student/json/student3.json");

    File bookSetFile1 = new File("src/test/resources/repoTest/book/json/book_set_1.json");
    File bookSetFile2 = new File("src/test/resources/repoTest/book/json/book_set_2.json");
    File bookSetFile3 = new File("src/test/resources/repoTest/book/json/book_set_3.json");

    StudentEntity student1;
    StudentEntity student2;
    StudentEntity student3;

    Set<BookEntity> bookSet1;
    Set<BookEntity> bookSet2;
    Set<BookEntity> bookSet3;

    try {
      student1 = new ObjectMapper().readValue(studentFile1, new TypeReference<>() {});
      student2 = new ObjectMapper().readValue(studentFile2, new TypeReference<>() {});
      student3 = new ObjectMapper().readValue(studentFile3, new TypeReference<>() {});
      bookSet1 = new ObjectMapper().readValue(bookSetFile1, new TypeReference<>() {});
      bookSet2 = new ObjectMapper().readValue(bookSetFile2, new TypeReference<>() {});
      bookSet3 = new ObjectMapper().readValue(bookSetFile3, new TypeReference<>() {});
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    bookRepo.saveAll(bookSet1);
    student3.setBookSet(bookSet3);

    studentRepo.save(student1);
    bookRepo.saveAll(bookSet2);
    bookRepo.saveAll(bookSet3);

    student1.setBookSet(bookSet1);
    student2.setBookSet(bookSet2);
    studentRepo.save(student2);
    studentRepo.save(student3);
  }


  @DisplayName("Should find all books by author")
  @Test
  void shouldFindAllByAuthor() {
    // given
    String author = "Joanne Rowling";
    Page<BookEntity> bookPage = bookRepo.findAllByAuthor(author, pageable);
    List<BookEntity> bookList = bookPage.getContent();
    // then
    assertThat(bookList).hasSize(2)
        .extracting(BookEntity::getAuthor)
        .allMatch(setAuthor -> setAuthor.contains(author));
  }

  @DisplayName("Should find all books by series")
  @Test
  void shouldFindAllBySeries() {
    // given
    String series = "A Song of Ice and Fire";
    Page<BookEntity> bookPage = bookRepo.findAllBySeries(series, pageable);
    List<BookEntity> bookList = bookPage.getContent();
    // then
    assertThat(bookList).hasSize(4)
        .extracting(BookEntity::getSeries)
        .allMatch(bookSeries -> bookSeries.equals(series));
  }

  @DisplayName("Should find all books by genre")
  @Test
  void shouldFindAllByGenre() {
    // given
    GenreEnum genreEnum = FANTASY;
    Page<BookEntity> bookPage = bookRepo.findAllByGenre(genreEnum.name(), pageable);
    List<BookEntity> bookList = bookPage.getContent();
    // then
    assertThat(bookList).hasSize(8)
        .extracting(BookEntity::getGenre)
        .allMatch(genreSet -> genreSet.stream().anyMatch(genre -> genre.equals(genreEnum)));
  }

  @DisplayName("Should find all books by year of publication")
  @Test
  void shouldFindAllByPublicationYear() {
    // given
    Integer year = 1997;
    Page<BookEntity> bookPage = bookRepo.findAllByPublicationYear(year, pageable);
    List<BookEntity> bookList = bookPage.getContent();
    // then
    assertThat(bookList).hasSize(2)
        .extracting(BookEntity::getPublicationYear)
        .allMatch(bookYear -> bookYear.equals(year));
  }

  @DisplayName("Should find all books by student's id")
  @Test
  void shouldFindAllByStudentId() {
    // given
    Long studentId = 2L;
    String studentName = "Elena";
    Integer studentAge = 20;
    String studentPhone = "+7(044)024-74-77";
    GenderEnum studentGender = GenderEnum.FEMALE;

    Optional<StudentEntity> studentEntity = studentRepo.findById(studentId);
    // then
    assertThat(studentEntity).isPresent()
        .hasValueSatisfying((student) -> {
          assertThat(student.getName()).isEqualTo(studentName);
          assertThat(student.getAge()).isEqualTo(studentAge);
          assertThat(student.getPhone()).isEqualTo(studentPhone);
          assertThat(student.getGender()).isEqualTo(studentGender);
        });
    //given
    Set<BookEntity> bookSet = studentEntity.get().getBookSet();
    //then
    assertThat(bookSet).hasSize(4)
        .extracting(BookEntity::getGenre)
        .allMatch(
            genreSet -> genreSet.stream()
                .anyMatch(genre -> genre.equals(FANTASY))
        );
  }

  @DisplayName("Should find book by name")
  @Test
  void shouldFindByName() {
    // given
    String bookName = "The Visitor";
    String bookSeries = "Jack Reacher";
    Set<String> bookAuthors = Set.of("Lee Child");
    Integer bookPublicationYear = 2000;
    Integer bookQuantity = 5;
    Set<GenreEnum> bookGenres = Set.of(FOREIGN, ADVENTURES, DETECTIVE);

    Optional<BookEntity> optionalBook = bookRepo.findByName(bookName);
    // then
    assertThat(optionalBook).isPresent()
        .hasValueSatisfying(book -> {
          assertThat(book.getName()).isEqualTo(bookName);
          assertThat(book.getSeries()).isEqualTo(bookSeries);
          assertThat(book.getAuthor()).isEqualTo(bookAuthors);
          assertThat(book.getPublicationYear()).isEqualTo(bookPublicationYear);
          assertThat(book.getQuantity()).isEqualTo(bookQuantity);
          assertThat(book.getGenre()).isEqualTo(bookGenres);
        });
  }

  @DisplayName("Should return true if book exist by name")
  @Test
  void shouldReturnTrueIfBookExistByName() {
    // given
    String name = "The Adventures of Dunno and his Friends";
    boolean isExist = bookRepo.existsByName(name);
    // then
    assertThat(isExist).isTrue();
  }

  @DisplayName("Should return false if book does not exist by name")
  @Test
  void shouldReturnFalseIfBookDoesNotExistByName() {
    // given
    String name = "Some random name";
    boolean isExist = bookRepo.existsByName(name);
    // then
    assertThat(isExist).isFalse();
  }

  @DisplayName("Should delete book by name")
  @Test
  void shouldDeleteByName() {
    // given
    String name = "The Adventures of Dunno and his Friends";
    Optional<BookEntity> deleted = bookRepo.findByName(name);
    // then
    assertThat(deleted).isPresent();
    // when
    bookRepo.deleteByName(name);
    //then
    assertThat(bookRepo.existsByName(name)).isFalse();

    bookRepo.save(deleted.get());
  }
}
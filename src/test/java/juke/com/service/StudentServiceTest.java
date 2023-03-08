package juke.com.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Optional;
import juke.com.entity.StudentEntity;
import juke.com.entity.enums.GenderEnum;
import juke.com.repository.IBookRepository;
import juke.com.repository.IStudentRepository;
import juke.com.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private IStudentRepository studentRepo;

  @Mock
  private IBookRepository bookRepo;

  @Mock
  private IBookService bookService;

  private IStudentService underTest;
  private Pageable pageable;

  @BeforeEach
  void beforeEach() {
    underTest = new StudentServiceImpl(studentRepo, bookRepo, bookService);
    pageable = PageRequest.of(0, 100);
  }


  @DisplayName("Should call \"findAll()\"")
  @Test
  void shouldCallFindAll() {
    // when
    underTest.findAll(pageable);
    // then
    then(studentRepo).should().findAll(pageable);
  }

  @DisplayName("Should call \"findAllByGender()\"")
  @Test
  void shouldCallFindAllByGender() {
    // given
    String genderString = "FEMALE";
    GenderEnum gender = GenderEnum.valueOf(genderString);
    // when
    underTest.findAllByGender(genderString, pageable);
    // then
    then(studentRepo).should().findAllByGender(gender, pageable);
  }

  @DisplayName("Should call \"findAllByAge()\"")
  @Test
  void shouldCallFindAllByAge() {
    // given
    Integer age = 22;
    // when
    underTest.findAllByAge(age, pageable);
    // then
    then(studentRepo).should().findAllByAge(age, pageable);
  }

  @DisplayName("Should call \"findAllByBookId()\" if book exist")
  @Test
  void shouldCallFindAllByBookIdIfBookExist() {
    // given
    Long bookId = 1L;
    given(bookRepo.existsById(bookId)).willReturn(true);
    // when
    underTest.findAllByBookId(bookId, pageable);
    // then
    then(studentRepo).should().findAllByBookId(bookId, pageable);
  }

  @DisplayName("Should throw exception when call \"findAllByBookId()\" if book does not exist")
  @Test
  void shouldThrowExceptionWhenCallFindAllByBookIdIfBookDoesNotExist() {
//    if a lot of users need pagination
    Long bookId = 1L;
    String message = String.format("Unable to find a book with id = [%d]!", bookId);
    given(bookRepo.existsById(bookId)).willReturn(false);
    // then
    assertThatThrownBy(() -> underTest.findAllByBookId(bookId, pageable))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(message);
  }

  @DisplayName("Should call \"findById()\" if student exist")
  @Test
  void shouldCallFindByIdIfStudentExist() {
    // given
    Long id = 1L;
    Optional<StudentEntity> studentOptional = Optional.of(new StudentEntity());
    given(studentRepo.findById(id)).willReturn(studentOptional);
    // when
    underTest.findById(id);
    // then
    then(studentRepo).should().findById(id);
  }

  @DisplayName("Should throw exception when call \"findById()\" if student does not exist")
  @Test
  void shouldThrowExceptionWhenCallFindByIdIfStudentDoesNotExist() {
    // given
    Long id = 1L;
    Optional<StudentEntity> empty = Optional.empty();
    String message = String.format("Unable to find a student with id = [%d]!", id);

    given(studentRepo.findById(id)).willReturn(empty);
    // then
    assertThatThrownBy(() -> underTest.findById(id))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(message);
  }

  @DisplayName("Should call \"findByName()\" if student exist")
  @Test
  void shouldCallFindByNameIfStudentExist() {
    // given
    String name = "Some name";
    Optional<StudentEntity> studentOptional = Optional.of(new StudentEntity());
    given(studentRepo.findByName(name)).willReturn(studentOptional);
    // when
    underTest.findByName(name);
    // then
    then(studentRepo).should().findByName(name);
  }

  @DisplayName("Should throw exception when call \"findByName()\" if student does not exist")
  @Test
  void shouldThrowExceptionWhenCallFindByNameIfStudentDoesNotExist() {
    // given
    String name = "Some name";
    Optional<StudentEntity> empty = Optional.empty();
    String message = String.format("Unable to find a student with name = [%s]!", name);

    given(studentRepo.findByName(name)).willReturn(empty);
    // then
    assertThatThrownBy(() -> underTest.findByName(name))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(message);
  }

  @DisplayName("Should call \"findByPhone()\" if student exist")
  @Test
  void shouldCallFindByPhoneIfStudentExist() {
    // given
    String phone = "+7(923)789-66-74";
    Optional<StudentEntity> studentOptional = Optional.of(new StudentEntity());
    given(studentRepo.findByPhone(phone)).willReturn(studentOptional);
    // when
    underTest.findByPhone(phone);
    // then
    then(studentRepo).should().findByPhone(phone);
  }

  @DisplayName("Should throw exception when call \"findByPhone()\" if student does not exist")
  @Test
  void shouldThrowExceptionWhenCallFindByPhoneIfStudentDoesNotExist() {
    // given
    String phone = "+7(923)789-66-74";
    Optional<StudentEntity> empty = Optional.empty();
    String message = String.format("Unable to find a student with phone = [%s]!", phone);

    given(studentRepo.findByPhone(phone)).willReturn(empty);
    // then
    assertThatThrownBy(() -> underTest.findByPhone(phone))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(message);
  }


  @DisplayName("Should call \"save()\" if student does not exist")
  @Test
  void shouldCallSaveIfStudentDoesNotExist() {
    // given
    StudentEntity student = new StudentEntity();
    // when
    underTest.save(student);
    // then
    then(studentRepo).should().save(student);
  }

  @DisplayName("Should throw exception when call \"save()\" if student exist by name")
  @Test
  void shouldThrowExceptionWhenCallSaveIfStudentExistByName() {
    // given
    String name = "Alex";
    String message = String.format("The student with name = [%s] is already exist!", name);
    StudentEntity student = new StudentEntity();
    student.setName(name);

    given(studentRepo.existsByName(name)).willReturn(true);
    // then
    assertThatThrownBy(() -> underTest.save(student))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(message);
  }

  @Disabled
  @Test
  void shouldUpdateName() {
    // given
    // when
    // then
  }

  @Disabled
  @Test
  void shouldUpdateAge() {
    // given
    // when
    // then
  }

  @Disabled
  @Test
  void shouldUpdatePhone() {
    // given
    // when
    // then
  }

  @Disabled
  @Test
  void shouldUpdateGender() {
    // given
    // when
    // then
  }

  @Disabled
  @Test
  void shouldAddBook() {
    // given
    // when
    // then
  }

  @Disabled
  @Test
  void shouldRemoveBook() {
    // given
    // when
    // then
  }

  @Disabled
  @Test
  void shouldDeleteById() {
    // given
    // when
    // then
  }

  @Disabled
  @Test
  void shouldDeleteByName() {
    // given
    // when
    // then
  }

  @Disabled
  @Test
  void shouldDeleteByPhone() {
    // given
    // when
    // then
  }
}
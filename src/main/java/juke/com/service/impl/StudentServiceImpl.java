package juke.com.service.impl;

import java.util.List;
import java.util.Set;
import juke.com.entity.BookEntity;
import juke.com.entity.StudentEntity;
import juke.com.entity.enums.GenderEnum;
import juke.com.repository.IBookRepository;
import juke.com.repository.IStudentRepository;
import juke.com.service.IBookService;
import juke.com.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements IStudentService {

  private final IStudentRepository studentRepo;
  private final IBookRepository bookRepo;
  private final IBookService bookService;

  @Override
  public Page<StudentEntity> findAll(Pageable pageable) {
    return studentRepo.findAll(pageable);
  }

  @Override
  public Page<StudentEntity> findAllByGender(String gender, Pageable pageable) {
    return studentRepo.findAllByGender(GenderEnum.valueOf(gender.toUpperCase()), pageable);
  }

  @Override
  public Page<StudentEntity> findAllByAge(Integer age, Pageable pageable) {
    return studentRepo.findAllByAge(age, pageable);
  }

  @Override
  public Page<StudentEntity> findAllByBookId(Long bookId, Pageable pageable) {

    if (!bookRepo.existsById(bookId)) {
      throw new IllegalArgumentException(
          String.format("Unable to find a book with id = [%d]!", bookId));
    }

    return studentRepo.findAllByBookId(bookId, pageable);
  }

  @Override
  public StudentEntity findById(Long id) {
    return studentRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("Unable to find a student with id = [%d]!", id)));
  }

  @Override
  public StudentEntity findByName(String name) {
    return studentRepo.findByName(name)
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("Unable to find a student with name = [%s]!", name)));
  }

  @Override
  public StudentEntity findByPhone(String phone) {
    return studentRepo.findByPhone(phone)
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("Unable to find a student with phone = [%s]!", phone)));
  }

  @Override
  public StudentEntity save(StudentEntity student) {

    if (studentRepo.existsByName(student.getName())) {
      throw new IllegalArgumentException(
          String.format("The student with name = [%s] is already exist!", student.getName()));
    }

    if (studentRepo.existsByPhone(student.getPhone())) {
      throw new IllegalArgumentException(
          String.format("The student with phone = [%s] is already exist!", student.getPhone()));
    }

    return studentRepo.save(student);
  }

  @Override
  public List<StudentEntity> saveAll(List<StudentEntity> studentList) {

    for (StudentEntity student : studentList) {
      if (studentRepo.existsByName(student.getName())) {
        throw new IllegalArgumentException(
            String.format("The student with name [%s] is already exist", student.getName()));
      }
    }

    return studentRepo.saveAll(studentList);
  }

  @Override
  public StudentEntity updateName(Long id, String name) {

    if (!studentRepo.existsById(id)) {
      throw new IllegalArgumentException(
          String.format("The student with id = [%d] does not exist!", id));
    }

    StudentEntity student = this.findById(id);
    student.setName(name);

    return studentRepo.save(student);
  }

  @Override
  public StudentEntity updateAge(Long id, Integer age) {

    if (!studentRepo.existsById(id)) {
      throw new IllegalArgumentException(
          String.format("The student with id = [%d] does not exist!", id));
    }

    StudentEntity student = this.findById(id);
    student.setAge(age);

    return studentRepo.save(student);
  }

  @Override
  public StudentEntity updatePhone(Long id, String phone) {

    if (!studentRepo.existsById(id)) {
      throw new IllegalArgumentException(
          String.format("The student with id = [%d] does not exist!", id));
    }

    StudentEntity student = this.findById(id);
    student.setPhone(phone);

    return studentRepo.save(student);
  }

  @Override
  public StudentEntity updateGender(Long id, String gender) {

    if (!studentRepo.existsById(id)) {
      throw new IllegalArgumentException(
          String.format("The student with id = [%d] does not exist!", id));
    }

    StudentEntity student = this.findById(id);
    student.setGender(GenderEnum.valueOf(gender));

    return studentRepo.save(student);
  }

  @Override
  public StudentEntity addBook(Long bookId, Long studentId) {

    BookEntity bookEntity = bookService.findById(bookId);
    StudentEntity studentEntity = this.findById(studentId);

    boolean isStudentHaveBook = studentEntity.getBookSet().stream()
        .anyMatch(book -> book.getName().equals(bookEntity.getName()));

    if (isStudentHaveBook) {
      throw new IllegalArgumentException(
          String.format("The student does already have a book with id = [%d]", bookId));
    }

    Set<BookEntity> bookList = studentEntity.getBookSet();

    if (bookEntity.getQuantity() == 0) {
      throw new IllegalStateException(
          String.format("There are no books with name = [%s] left on stock", bookEntity.getName()));
    }

    bookList.add(bookEntity);
    bookEntity.setQuantity(bookEntity.getQuantity() - 1);
    bookRepo.save(bookEntity);

    studentEntity.setBookSet(bookList);
    return studentRepo.save(studentEntity);
  }

  @Override
  public StudentEntity removeBook(Long bookId, Long studentId) {

    BookEntity bookEntity = bookService.findById(bookId);
    StudentEntity studentEntity = this.findById(studentId);

    boolean isStudentHaveBook = studentEntity.getBookSet().stream()
        .anyMatch(book -> book.getName().equals(bookEntity.getName()));

    if (!isStudentHaveBook) {
      throw new IllegalArgumentException(
          String.format("The student does not have a book with id = [%d]", bookId));
    }

    Set<BookEntity> bookList = studentEntity.getBookSet();
    bookList.remove(bookEntity);
    studentEntity.setBookSet(bookList);

    bookEntity.setQuantity(bookEntity.getQuantity() + 1);
    bookRepo.save(bookEntity);

    return studentRepo.save(studentEntity);
  }

  @Override
  public void deleteById(Long id) {

    if (!studentRepo.existsById(id)) {
      throw new IllegalArgumentException(
          String.format("The student with id = [%d] does not exist!", id));
    }

    studentRepo.deleteById(id);
  }

  @Override
  public void deleteByName(String name) {

    if (!studentRepo.existsByName(name)) {
      throw new IllegalArgumentException(
          String.format("The student with name = [%s] does not exist!", name));
    }

    studentRepo.deleteByName(name);
  }

  @Override
  public void deleteByPhone(String phone) {

    if (!studentRepo.existsByPhone(phone)) {
      throw new IllegalArgumentException(
          String.format("The student with name = [%s] does not exist!", phone));
    }

    studentRepo.deleteByPhone(phone);
  }
}
package juke.com.service;

import java.util.List;
import juke.com.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IStudentService {

  Page<StudentEntity> findAll(Pageable pageable);

  Page<StudentEntity> findAllByGender(String gender, Pageable pageable);

  Page<StudentEntity> findAllByAge(Integer age, Pageable pageable);

  Page<StudentEntity> findAllByBookId(Long bookId, Pageable pageable);

  StudentEntity findById(Long id);

  StudentEntity findByName(String name);

  StudentEntity findByPhone(String phone);

  StudentEntity save(StudentEntity student);

  List<StudentEntity> saveAll(List<StudentEntity> studentList);

  StudentEntity updateName(Long id, String name);

  StudentEntity updateAge(Long id, Integer age);

  StudentEntity updatePhone(Long id, String phone);

  StudentEntity updateGender(Long id, String gender);

  StudentEntity addBook(Long bookId, Long studentId);

  StudentEntity removeBook(Long bookId, Long studentId);

  void deleteById(Long id);

  void deleteByName(String name);

  void deleteByPhone(String phone);
}
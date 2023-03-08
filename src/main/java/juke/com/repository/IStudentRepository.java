package juke.com.repository;

import java.util.Optional;
import juke.com.entity.StudentEntity;
import juke.com.entity.enums.GenderEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentRepository extends JpaRepository<StudentEntity, Long> {

  Page<StudentEntity> findAllByGender(GenderEnum gender, Pageable pageable);

  Page<StudentEntity> findAllByAge(Integer age, Pageable pageable);

  @Query(value = """
      select *
      from students
      where id in (
        select student_id
        from "STUDENTS_BOOKS"
        where  book_id = ?1
      )""", nativeQuery = true)
  Page<StudentEntity> findAllByBookId(Long bookId, Pageable pageable);

  Optional<StudentEntity> findByPhone(String phone);

  Optional<StudentEntity> findByName(String name);

  boolean existsByName(String name);

  boolean existsByPhone(String phone);

  void deleteByName(String name);

  void deleteByPhone(String phone);
}
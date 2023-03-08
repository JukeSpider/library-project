package juke.com.repository;

import java.util.Optional;
import juke.com.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRepository extends JpaRepository<BookEntity, Long> {

  @Query(value = """
      SELECT *
      FROM books
      WHERE id in (
        select book_id
        from authors
        where author = ?1
      )
      """,
      nativeQuery = true)
  Page<BookEntity> findAllByAuthor(String authorName, Pageable pageable);

  Page<BookEntity> findAllBySeries(String series, Pageable pageable);

  @Query(value = """
      SELECT *
      FROM books
      WHERE id in (
        select book_id
        from genres
        where genre = ?1
      )
      """,
      nativeQuery = true)
  Page<BookEntity> findAllByGenre(String genre, Pageable pageable);

  Page<BookEntity> findAllByPublicationYear(Integer year, Pageable pageable);

  @Query(value = """
      SELECT *
      FROM books
      WHERE id in (
        select book_id
        from "students_books"
        where student_id = ?1
      )
      """,
      nativeQuery = true)
  Page<BookEntity> findAllByStudentId(Long id, Pageable pageable);

  Optional<BookEntity> findByName(String name);

  boolean existsByName(String name);

  void deleteByName(String name);
}
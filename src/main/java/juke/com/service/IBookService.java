package juke.com.service;

import java.util.List;
import java.util.Set;
import juke.com.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBookService {

  Page<BookEntity> findAll(Pageable pageable);

  Page<BookEntity> findAllByAuthor(String authorName, Pageable pageable);

  Page<BookEntity> findAllBySeries(String series, Pageable pageable);

  Page<BookEntity> findAllByGenre(String genre, Pageable pageable);

  Page<BookEntity> findAllByPublicationYear(Integer year, Pageable pageable);

  Page<BookEntity> findAllByStudentId(Long studentId, Pageable pageable);

  BookEntity findById(Long id);

  BookEntity findByName(String name);

  BookEntity save(BookEntity book);

  List<BookEntity> saveAll(List<BookEntity> bookList);

  BookEntity updateName(Long id, String newName);

  BookEntity updateSeries(Long id, String newSeries);

  BookEntity updateAuthorSet(Long id, Set<String> newAuthorList);

  BookEntity updatePublicationYear(Long id, Integer newYear);

  BookEntity updateQuantity(Long id, Integer newQuantity);

  BookEntity updateGenreSet(Long id, Set<String> newGenreSet);

  void deleteById(Long id);

  void deleteByName(String name);
}
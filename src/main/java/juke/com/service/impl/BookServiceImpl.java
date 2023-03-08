package juke.com.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import juke.com.entity.BookEntity;
import juke.com.entity.enums.GenreEnum;
import juke.com.repository.IBookRepository;
import juke.com.repository.IStudentRepository;
import juke.com.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements IBookService {

  private final IBookRepository bookRepo;
  private final IStudentRepository studentRepo;

  @Override
  public Page<BookEntity> findAll(Pageable pageable) {
    return bookRepo.findAll(pageable);
  }

  @Override
  public Page<BookEntity> findAllByAuthor(String authorName, Pageable pageable) {
    return bookRepo.findAllByAuthor(authorName, pageable);
  }

  @Override
  public Page<BookEntity> findAllBySeries(String series, Pageable pageable) {
    return bookRepo.findAllBySeries(series, pageable);
  }

  @Override
  public Page<BookEntity> findAllByGenre(String genre, Pageable pageable) {
    return bookRepo.findAllByGenre(genre.toUpperCase(), pageable);
  }

  @Override
  public Page<BookEntity> findAllByPublicationYear(Integer year, Pageable pageable) {
    return bookRepo.findAllByPublicationYear(year, pageable);
  }

  @Override
  public Page<BookEntity> findAllByStudentId(Long studentId, Pageable pageable) {

    if (!studentRepo.existsById(studentId)) {
      throw new IllegalArgumentException(
          String.format("Unable to find a student with id = [%d]!", studentId));
    }

    return bookRepo.findAllByStudentId(studentId, pageable);
  }

  @Override
  public BookEntity findById(Long id) {
    return bookRepo.findById(id).orElseThrow(() -> new IllegalArgumentException(
        String.format("Unable to find a book with id = [%d]!", id)));
  }

  @Override
  public BookEntity findByName(String name) {
    return bookRepo.findByName(name)
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("Unable to find a book with name = [%s]!", name)));
  }

  @Override
  public BookEntity save(BookEntity book) {

    if (bookRepo.existsByName(book.getName())) {
      throw new IllegalArgumentException(
          String.format("The book with name = [%s] is already exist!", book.getName()));
    }

    return bookRepo.save(book);
  }

  @Override
  public List<BookEntity> saveAll(List<BookEntity> bookList) {

    for (BookEntity book : bookList) {
      if (bookRepo.existsByName(book.getName())) {
        throw new IllegalArgumentException(
            String.format("The book with name = [%s] is already exist!", book.getName()));
      }
    }

    return bookRepo.saveAll(bookList);
  }

  @Override
  public BookEntity updateName(Long id, String newName) {

    if (!bookRepo.existsById(id)) {
      throw new IllegalArgumentException(
          String.format("The book with id = [%d] does not exist!", id));
    }

    BookEntity book = this.findById(id);
    book.setName(newName);

    return bookRepo.save(book);
  }

  @Override
  public BookEntity updateSeries(Long id, String newSeries) {

    if (!bookRepo.existsById(id)) {
      throw new IllegalArgumentException(
          String.format("The book with id = [%d] does not exist!", id));
    }

    BookEntity book = this.findById(id);
    book.setSeries(newSeries);

    return bookRepo.save(book);
  }

  @Override
  public BookEntity updateAuthorSet(Long id, Set<String> newAuthorSet) {

    if (!bookRepo.existsById(id)) {
      throw new IllegalArgumentException(
          String.format("The book with id = [%d] does not exist!", id));
    }

    BookEntity book = this.findById(id);
    book.setAuthor(newAuthorSet);

    return bookRepo.save(book);
  }

  @Override
  public BookEntity updatePublicationYear(Long id, Integer newYear) {

    if (!bookRepo.existsById(id)) {
      throw new IllegalArgumentException(
          String.format("The book with id = [%d] does not exist!", id));
    }

    BookEntity book = this.findById(id);
    book.setPublicationYear(newYear);

    return bookRepo.save(book);
  }

  @Override
  public BookEntity updateQuantity(Long id, Integer newQuantity) {

    if (!bookRepo.existsById(id)) {
      throw new IllegalArgumentException(
          String.format("The book with id = [%d] does not exist!", id));
    }

    BookEntity book = this.findById(id);
    book.setQuantity(newQuantity);

    return bookRepo.save(book);
  }

  @Override
  public BookEntity updateGenreSet(Long id, Set<String> newGenreSet) {

    if (!bookRepo.existsById(id)) {
      throw new IllegalArgumentException(
          String.format("The book with id = [%d] does not exist!", id));
    }

    Set<GenreEnum> newGenreEnumSet = newGenreSet.stream()
        .map(genre -> GenreEnum.valueOf(genre.toUpperCase()))
        .collect(Collectors.toSet());

    BookEntity book = this.findById(id);
    book.setGenre(newGenreEnumSet);

    return bookRepo.save(book);
  }

  @Override
  public void deleteById(Long id) {

    if (!bookRepo.existsById(id)) {
      throw new IllegalArgumentException(
          String.format("The book with id = [%d] does not exist!", id));
    }

    bookRepo.deleteById(id);
  }

  @Override
  public void deleteByName(String name) {

    if (!bookRepo.existsByName(name)) {
      throw new IllegalArgumentException(
          String.format("The book with name = [%s] does not exist!", name));
    }

    bookRepo.deleteByName(name);
  }
}
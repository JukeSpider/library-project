package juke.com.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import juke.com.dto.BookDto;
import juke.com.entity.BookEntity;
import juke.com.mapper.IBookMapper;
import juke.com.service.IBookService;
import juke.com.validator.IBookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/book")
@RestController
public class BookController {

  private final IBookMapper mapper;
  private final IBookService service;
  private final IBookValidator validator;

  @GetMapping("/all")
  public ResponseEntity<Page<BookDto>> findAll(
      @PageableDefault(sort = {"id"}) Pageable pageable
  ) {
    Page<BookEntity> bookEntityPage = service.findAll(pageable);
    List<BookDto> bookDtoList = mapper.toDtoList(bookEntityPage.getContent());

    return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(bookDtoList));
  }

  @GetMapping("/all/author/{authorName}")
  public ResponseEntity<Page<BookDto>> findAllByAuthor(
      @PathVariable String authorName,
      @PageableDefault(sort = {"id"}) Pageable pageable
  ) {
    String name = String.join(" ", authorName.split("_"));

    validator.isValidAuthorName(name);

    Page<BookEntity> bookEntityPage = service.findAllByAuthor(name, pageable);
    List<BookDto> bookDtoList = mapper.toDtoList(bookEntityPage.getContent());

    return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(bookDtoList));
  }

  @GetMapping("/all/series/{series}")
  public ResponseEntity<Page<BookDto>> findAllBySeries(
      @PathVariable String series,
      @PageableDefault(sort = {"id"}) Pageable pageable
  ) {
    String name = String.join(" ", series.split("_"));

    validator.isValidSeries(name);

    Page<BookEntity> bookEntityPage = service.findAllBySeries(name, pageable);
    List<BookDto> bookDtoList = mapper.toDtoList(bookEntityPage.getContent());

    return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(bookDtoList));
  }

  @GetMapping("/all/genre/{genre}")
  public ResponseEntity<Page<BookDto>> findAllByGenre(
      @PathVariable String genre,
      @PageableDefault(sort = {"id"}) Pageable pageable
  ) {
    validator.isValidGenre(genre);

    Page<BookEntity> bookEntityPage = service.findAllByGenre(genre.toUpperCase(), pageable);
    List<BookDto> bookDtoList = mapper.toDtoList(bookEntityPage.getContent());

    return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(bookDtoList));
  }

  @GetMapping("/all/year/{year}")
  public ResponseEntity<Page<BookDto>> findAllByYear(
      @PathVariable Integer year,
      @PageableDefault(sort = {"id"}) Pageable pageable
  ) {
    validator.isValidPublicationYear(year);

    Page<BookEntity> bookEntityPage = service.findAllByPublicationYear(year, pageable);
    List<BookDto> bookDtoList = mapper.toDtoList(bookEntityPage.getContent());

    return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(bookDtoList));
  }

  @GetMapping("/all/student/{studentId}")
  public ResponseEntity<Page<BookDto>> findAllByStudentId(
      @PathVariable Long studentId,
      @PageableDefault(sort = {"id"}) Pageable pageable
  ) {
    validator.isValidId(studentId);

    Page<BookEntity> bookEntityPage = service.findAllByStudentId(studentId, pageable);
    List<BookDto> bookDtoList = mapper.toDtoList(bookEntityPage.getContent());

    return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(bookDtoList));
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<BookDto> findById(@PathVariable Long id) {

    validator.isValidId(id);

    BookEntity bookEntity = service.findById(id);
    return ResponseEntity.status(HttpStatus.OK).body(mapper.toDto(bookEntity));
  }

  @GetMapping("/name/{bookName}")
  public ResponseEntity<BookDto> findByName(@PathVariable String bookName) {

    String name = String.join(" ", bookName.split("_"));

    validator.isValidName(name);

    BookEntity bookEntity = service.findByName(name);
    return ResponseEntity.status(HttpStatus.OK).body(mapper.toDto(bookEntity));
  }

  @PostMapping("/save")
  public ResponseEntity<BookDto> save(@RequestBody BookDto bookDto) {

    validator.isValidBook(bookDto);

    BookEntity bookEntity = mapper.toEntity(bookDto);
    bookEntity = service.save(bookEntity);

    return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(bookEntity));
  }

  @PostMapping("/all/save")
  public ResponseEntity<List<BookDto>> saveAll(@RequestBody List<BookDto> bookDtoList) {

    validator.isValidBookList(bookDtoList);

    List<BookEntity> bookEntityList = mapper.toEntityList(bookDtoList);
    bookEntityList = service.saveAll(bookEntityList);

    return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDtoList(bookEntityList));
  }

  @PutMapping("/update/name/{id}")
  public ResponseEntity<BookDto> updateName(@PathVariable Long id, @RequestBody String name) {

    validator.isValidId(id);
    validator.isValidName(name);

    BookEntity bookEntity = service.updateName(id, name);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(mapper.toDto(bookEntity));
  }

  @PutMapping("/update/series/{id}")
  public ResponseEntity<BookDto> updateSeries(@PathVariable Long id, @RequestBody String series) {

    validator.isValidId(id);
    validator.isValidSeries(series);

    BookEntity bookEntity = service.updateSeries(id, series);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(mapper.toDto(bookEntity));
  }

  @PutMapping("/update/author/{id}")
  public ResponseEntity<BookDto> updateAuthorSet(
      @PathVariable Long id,
      @RequestBody Set<String> authorSet
  ) {
    validator.isValidId(id);
    validator.isValidAuthorSet(authorSet);

    BookEntity bookEntity = service.updateAuthorSet(id, authorSet);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(mapper.toDto(bookEntity));
  }

  @PutMapping("/update/year/{id}")
  public ResponseEntity<BookDto> updatePublicationYear(
      @PathVariable Long id,
      @RequestBody Integer year
  ) {
    validator.isValidId(id);
    validator.isValidPublicationYear(year);

    BookEntity bookEntity = service.updatePublicationYear(id, year);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(mapper.toDto(bookEntity));
  }

  @PutMapping("/update/quantity/{id}")
  public ResponseEntity<BookDto> updateQuantity(
      @PathVariable Long id,
      @RequestBody Integer quantity
  ) {
    validator.isValidId(id);
    validator.isValidQuantity(quantity);

    BookEntity bookEntity = service.updateQuantity(id, quantity);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(mapper.toDto(bookEntity));
  }

  @PutMapping("/update/genre/{id}")
  public ResponseEntity<BookDto> updateGenreSet(
      @PathVariable Long id,
      @RequestBody Set<String> genre
  ) {
    validator.isValidId(id);
    validator.isValidGenreSet(genre);

    BookEntity bookEntity = service.updateGenreSet(id, genre);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(mapper.toDto(bookEntity));
  }

  @DeleteMapping("/delete/id/{id}")
  public void deleteById(@PathVariable Long id) {
    validator.isValidId(id);
    service.deleteById(id);
  }

  @DeleteMapping("/delete/name/{name}")
  public void deleteByName(@PathVariable String name) {
    validator.isValidName(name);
    service.deleteByName(name);
  }
}
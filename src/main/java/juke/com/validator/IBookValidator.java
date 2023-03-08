package juke.com.validator;

import java.util.List;
import java.util.Set;
import juke.com.dto.BookDto;

public interface IBookValidator {

  void isValidBook(BookDto book);

  void isValidBookList(List<BookDto> bookList);

  void isValidId(Long id);

  void isValidName(String name);

  void isValidSeries(String series);

  void isValidAuthorSet(Set<String> authorSet);

  void isValidAuthorName(String authorName);

  void isValidPublicationYear(Integer publicationYear);

  void isValidQuantity(Integer quantity);

  void isValidGenreSet(Set<String> genreSet);

  void isValidGenre(String genre);
}
package juke.com.validator.impl;

import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import juke.com.dto.BookDto;
import juke.com.entity.enums.GenreEnum;
import juke.com.validator.IBookValidator;
import org.springframework.stereotype.Component;

@Component
public class BookValidatorImpl implements IBookValidator {

  @Override
  public void isValidBook(BookDto book) {

    if (book == null) {
      throw new IllegalArgumentException("Argument [studentDto] is null!");
    }

    isValidName(book.getName());
    isValidAuthorSet(book.getAuthor());
    isValidPublicationYear(book.getPublicationYear());
    isValidQuantity(book.getQuantity());
    isValidGenreSet(book.getGenre());
  }

  @Override
  public void isValidBookList(List<BookDto> bookList) {

    if (bookList == null || bookList.isEmpty()) {
      throw new IllegalArgumentException("Book list is empty!");
    }

    bookList.forEach(this::isValidBook);
  }

  @Override
  public void isValidId(Long id) {

    if (id == null || id.intValue() < 1) {
      throw new IllegalArgumentException(
          String.format("Id = [%d] is not valid!", id));
    }
  }

  @Override
  public void isValidName(String name) {

    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("The book's name cannot be empty!");
    }

    if (name.length() < 2 || name.length() > 100) {
      throw new IllegalArgumentException(
          "The length of the book name must be between 2 and 100 characters!");
    }
  }

  @Override
  public void isValidSeries(String series) {
    this.isValidName(series);
  }

  @Override
  public void isValidAuthorSet(Set<String> authorSet) {
    authorSet.forEach(this::isValidAuthorName);
  }

  @Override
  public void isValidAuthorName(String authorName) {

    String authorPattern = "^[A-Z][A-Za-z\\-]{2,} [A-Z][A-Za-z\\-]{2,}$";

    if (authorName == null || authorName.isBlank()) {
      throw new IllegalArgumentException("The author's name cannot be empty!");
    }

    if (authorName.length() < 2 || authorName.length() > 50) {
      throw new IllegalArgumentException(
          "The length of the author's name must be between 2 and 30 characters!");
    }

    if (!Pattern.compile(authorPattern).matcher(authorName).matches()) {
      throw new IllegalArgumentException(
          "The author's name must match the following pattern:\nXxxxx Xxxxx");
    }
  }

  @Override
  public void isValidPublicationYear(Integer publicationYear) {

    if (publicationYear == null) {
      throw new IllegalArgumentException("The year of publication cannot be empty!");
    }

    if (publicationYear < 1 || publicationYear > Year.now().getValue()) {
      throw new IllegalArgumentException(
          "The year of publication must be between 1 A.D. and current year!");
    }
  }

  @Override
  public void isValidQuantity(Integer quantity) {

    if (quantity == null) {
      throw new IllegalArgumentException("The quantity of books cannot be empty!");
    }

    if (quantity < 0) {
      throw new IllegalArgumentException("The quantity of books cannot be less than 1!");
    }
  }

  @Override
  public void isValidGenreSet(Set<String> genreSet) {
    genreSet.forEach(this::isValidGenre);
  }

  @Override
  public void isValidGenre(String genre) {

    boolean isNotValidEnum = Arrays.stream(GenreEnum.values())
        .noneMatch(g -> g.toString().equals(genre.toUpperCase()));

    if (isNotValidEnum) {
      throw new IllegalArgumentException("Non-existent genre!");
    }
  }
}
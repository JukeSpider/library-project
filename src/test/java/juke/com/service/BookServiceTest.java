package juke.com.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import juke.com.entity.BookEntity;
import juke.com.repository.IBookRepository;
import juke.com.repository.IStudentRepository;
import juke.com.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

  @Mock
  private IBookRepository bookRepo;

  @Mock
  private IStudentRepository studentRepo;

  private IBookService underTest;
  private Pageable pageable;

  @BeforeEach
  void beforeEach() {
    underTest = new BookServiceImpl(bookRepo, studentRepo);
    pageable = PageRequest.of(0, 100);
  }

  @DisplayName("Should call \"findAll()\"")
  @Test
  void shouldCallFindAll() {
    // when
    underTest.findAll(pageable);
    // then
    then(bookRepo).should().findAll(pageable);
  }

  @DisplayName("Should call \"findAllByAuthor()\"")
  @Test
  void shouldCallFindAllByAuthor() {
    // given
    String author = "Some Author";
    // when
    underTest.findAllByAuthor(author, pageable);
    // then
    then(bookRepo).should().findAllByAuthor(author, pageable);
  }

  @DisplayName("Should call \"findAllBySeries()\"")
  @Test
  void shouldCallFindAllBySeries() {
    // given
    String series = "Some series";
    // when
    underTest.findAllBySeries(series, pageable);
    // then
    then(bookRepo).should().findAllBySeries(series, pageable);
  }

  @DisplayName("Should call \"findAllByGenre()\"")
  @Test
  void shouldCallFindAllByGenre() {
    // given
    String genre = "SomeGenre";
    // when
    underTest.findAllByGenre(genre, pageable);
    // then
    then(bookRepo).should().findAllByGenre(genre.toUpperCase(), pageable);
  }

  @DisplayName("Should call \"findAllByPublicationYear()\"")
  @Test
  void shouldCallFindAllByPublicationYear() {
    // given
    Integer year = 2005;
    // when
    underTest.findAllByPublicationYear(year, pageable);
    // then
    then(bookRepo).should().findAllByPublicationYear(year, pageable);
  }

  @DisplayName("Should call \"findAllByStudentId()\" if student exist")
  @Test
  void shouldFindAllByStudentIdIfStudentExist() {
    // given
    Long studentId = 1L;
    given(studentRepo.existsById(studentId)).willReturn(true);
    // when
    underTest.findAllByStudentId(studentId, pageable);
    // then
    then(studentRepo).should().existsById(any());
    then(bookRepo).should().findAllByStudentId(studentId, pageable);
  }

  @DisplayName("Should throw exception when call \"findAllByStudentId()\" if student does not exist")
  @Test
  void shouldThrowExceptionWhenCallFindAllByStudentIdIfStudentDoesNotExist() {
    // given
    Long studentId = 1L;
    String message = String.format("Unable to find a student with id = [%d]!", studentId);
    given(studentRepo.existsById(studentId)).willReturn(false);
    // then
    assertThatThrownBy(() -> underTest.findAllByStudentId(studentId, pageable))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(message);
  }

  @DisplayName("Should call \"findById()\" if book exist")
  @Test
  void shouldCallFindByIdIfBookExist() {
    // given
    Long id = 1L;
    given(bookRepo.findById(id)).willReturn(Optional.of(new BookEntity()));
    // when
    underTest.findById(id);
    // then
    then(bookRepo).should().findById(id);
  }

  @DisplayName("Should throw exception when call \"findById()\" if book does not exist")
  @Test
  void shouldThrowExceptionWhenCallFindByIdIfBookDoesNotExist() {
    // given
    Long id = 1L;
    String message = String.format("Unable to find a book with id = [%d]!", id);
    given(bookRepo.findById(id)).willReturn(Optional.empty());
    // then
    assertThatThrownBy(() -> underTest.findById(id))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(message);
  }

  @DisplayName("Should call \"findByName()\" if book exist")
  @Test
  void shouldCallFindByNameIfBookExist() {
    // given
    String name = "Some Name";
    given(bookRepo.findByName(name)).willReturn(Optional.of(new BookEntity()));
    // when
    underTest.findByName(name);
    // then
    then(bookRepo).should().findByName(name);
  }

  @DisplayName("Should throw exception when call \"findByName()\" if book does not exist")
  @Test
  void shouldThrowExceptionWhenCallFindByNameIfBookDoesNotExist() {
    // given
    String name = "Some Name";
    String message = String.format("Unable to find a book with name = [%s]!", name);
    given(bookRepo.findByName(name)).willReturn(Optional.empty());
    // then
    assertThatThrownBy(() -> underTest.findByName(name))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(message);
  }

  @DisplayName("Should call \"save()\" if book does not exist")
  @Test
  void shouldCallSaveIfBookDoesNotExist() {
    //given
    BookEntity book = new BookEntity();
    given(bookRepo.existsByName(any())).willReturn(false);
    // when
    underTest.save(book);
    // then
    then(bookRepo).should().existsByName(any());
    then(bookRepo).should().save(book);
  }

  @DisplayName("Should throw exception when call \"save()\" if book exist")
  @Test
  void shouldThrowExceptionWhenCallSaveIfBookExist() {
    // given
    String name = "Some name";
    BookEntity book = new BookEntity();
    book.setName(name);
    String message = String.format("The book with name = [%s] is already exist!", book.getName());

    given(bookRepo.existsByName(any())).willReturn(true);
    // then
    assertThatThrownBy(() -> underTest.save(book))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(message);
  }

  @DisplayName("Should call \"saveAll()\" if all books do not exist")
  @Test
  void shouldCallSaveAllIfAllBooksDoNotExist() {
    // given
    List<BookEntity> bookList = List.of(new BookEntity(), new BookEntity());
    given(bookRepo.existsByName(any())).willReturn(false);
    // when
    underTest.saveAll(bookList);
    // then
    then(bookRepo).should(times(2)).existsByName(any());
    then(bookRepo).should().saveAll(bookList);
  }

  @DisplayName("Should throw exception when call \"saveAll()\" if any book exist")
  @Test
  void shouldThrowExceptionWhenCallSaveAllIfAnyBookExist() {
    // given
    String name = "Some name";
    BookEntity book = new BookEntity();
    book.setName(name);
    List<BookEntity> bookList = List.of(book, new BookEntity());
    String message = String.format("The book with name = [%s] is already exist!", book.getName());

    given(bookRepo.existsByName(any())).willReturn(true);
    // then
    assertThatThrownBy(() -> underTest.saveAll(bookList))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(message);
  }

  @DisplayName("Should call \"updateName()\" if book exist")
  @Test
  void shouldCallUpdateNameIfBookExist() {
    // given
    Long id = 1L;
    String name = "Some name";
    given(bookRepo.existsById(id)).willReturn(true);
    given(bookRepo.findById(id)).willReturn(Optional.of(new BookEntity()));
    // when
    underTest.updateName(id, name);
    // then
    then(bookRepo).should().existsById(id);
    then(bookRepo).should().findById(id);
    then(bookRepo).should().save(any());
  }

  @DisplayName("Should throw exception when call \"updateName()\" if book does not exist")
  @Test
  void shouldThrowExceptionWhenCallUpdateNameIfBookDoesNotExist() {
    // given
    Long id = 1L;
    String name = "Some name";
    String message = String.format("The book with id = [%d] does not exist!", id);

    given(bookRepo.existsById(id)).willReturn(false);
    // then
    assertThatThrownBy(() -> underTest.updateName(id, name))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(message);
  }

  @DisplayName("Should call \"updateSeries()\" if book exist")
  @Test
  void shouldCallUpdateSeriesIfBookExist() {
    // given
    Long id = 1L;
    String series = "Some series";
    given(bookRepo.existsById(id)).willReturn(true);
    given(bookRepo.findById(id)).willReturn(Optional.of(new BookEntity()));
    // when
    underTest.updateSeries(id, series);
    // then
    then(bookRepo).should().existsById(id);
    then(bookRepo).should().findById(id);
    then(bookRepo).should().save(any());
  }

  @DisplayName("Should throw exception when call \"updateSeries()\" if book does not exist")
  @Test
  void shouldThrowExceptionWhenCallUpdateSeriesIfBookDoesNotExist() {
    // given
    Long id = 1L;
    String series = "Some series";
    String message = String.format("The book with id = [%d] does not exist!", id);

    given(bookRepo.existsById(id)).willReturn(false);
    // then
    assertThatThrownBy(() -> underTest.updateSeries(id, series))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(message);
  }

  @DisplayName("Should call \"updateAuthorSet()\" if book exist")
  @Test
  void shouldCallUpdateAuthorSetIfBookExist() {
    // given
    Long id = 1L;
    Set<String> authorSet = Set.of("Author1", "Author2", "Author3");
    given(bookRepo.existsById(id)).willReturn(true);
    given(bookRepo.findById(id)).willReturn(Optional.of(new BookEntity()));
    // when
    underTest.updateAuthorSet(id, authorSet);
    // then
    then(bookRepo).should().existsById(id);
    then(bookRepo).should().findById(id);
    then(bookRepo).should().save(any());
  }

  @DisplayName("Should throw exception when call \"updateAuthorSet()\" if book does not exist")
  @Test
  void shouldThrowExceptionWhenCallUpdateAuthorSetIfBookDoesNotExist() {
    // given
    Long id = 1L;
    Set<String> authorSet = Set.of("Author1", "Author2", "Author3");
    String message = String.format("The book with id = [%d] does not exist!", id);

    given(bookRepo.existsById(id)).willReturn(false);
    // then
    assertThatThrownBy(() -> underTest.updateAuthorSet(id, authorSet))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(message);
  }

  @DisplayName("Should call \"updatePublicationYear()\" if book exist")
  @Test
  void shouldCallUpdatePublicationYearIfBookExist() {
    // given
    Long id = 1L;
    Integer year = 2000;
    given(bookRepo.existsById(id)).willReturn(true);
    given(bookRepo.findById(id)).willReturn(Optional.of(new BookEntity()));
    // when
    underTest.updatePublicationYear(id, year);
    // then
    then(bookRepo).should().existsById(id);
    then(bookRepo).should().findById(id);
    then(bookRepo).should().save(any());
  }

  @DisplayName("Should throw exception when call \"updatePublicationYear()\" if book does not exist")
  @Test
  void shouldThrowExceptionWhenCallUpdatePublicationYearIfBookDoesNotExist() {
    // given
    Long id = 1L;
    Integer year = 2000;
    String message = String.format("The book with id = [%d] does not exist!", id);

    given(bookRepo.existsById(id)).willReturn(false);
    // then
    assertThatThrownBy(() -> underTest.updatePublicationYear(id, year))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(message);
  }

  @DisplayName("Should call \"updateQuantity()\" if book exist")
  @Test
  void shouldCallUpdateQuantityIfBookExist() {
    // given
    Long id = 1L;
    Integer quantity = 10;
    given(bookRepo.existsById(id)).willReturn(true);
    given(bookRepo.findById(id)).willReturn(Optional.of(new BookEntity()));
    // when
    underTest.updateQuantity(id, quantity);
    // then
    then(bookRepo).should().existsById(id);
    then(bookRepo).should().findById(id);
    then(bookRepo).should().save(any());
  }

  @DisplayName("Should throw exception when call \"updateQuantity()\" if book does not exist")
  @Test
  void shouldThrowExceptionWhenCallUpdateQuantityIfBookDoesNotExist() {
    // given
    Long id = 1L;
    Integer quantity = 10;
    String message = String.format("The book with id = [%d] does not exist!", id);

    given(bookRepo.existsById(id)).willReturn(false);
    // then
    assertThatThrownBy(() -> underTest.updateQuantity(id, quantity))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(message);
  }

  @DisplayName("Should call \"updateGenreSet()\" if book exist")
  @Test
  void shouldCallUpdateGenreSetIfBookExist() {
    // given
    Long id = 1L;
    Set<String> genreSet = Set.of("humor", "horror");
    given(bookRepo.existsById(id)).willReturn(true);
    given(bookRepo.findById(id)).willReturn(Optional.of(new BookEntity()));
    // when
    underTest.updateGenreSet(id, genreSet);
    // then
    then(bookRepo).should().existsById(id);
    then(bookRepo).should().findById(id);
    then(bookRepo).should().save(any());
  }

  @DisplayName("Should throw exception when call \"updateGenreSet()\" if book does not exist")
  @Test
  void shouldThrowExceptionWhenCallUpdateGenreSetIfBookDoesNotExist() {
    // given
    Long id = 1L;
    Set<String> genreSet = Set.of("Genre1", "Genre2");
    String message = String.format("The book with id = [%d] does not exist!", id);

    given(bookRepo.existsById(id)).willReturn(false);
    // then
    assertThatThrownBy(() -> underTest.updateGenreSet(id, genreSet))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(message);
  }

  @DisplayName("Should call \"deleteById()\" if book exist")
  @Test
  void shouldCallDeleteByIdIfBookExist() {
    // given
    Long id = 1L;
    given(bookRepo.existsById(id)).willReturn(true);
    // when
    underTest.deleteById(id);
    // then
    then(bookRepo).should().existsById(id);
    then(bookRepo).should().deleteById(id);
  }

  @DisplayName("Should throw exception when call \"deleteById()\" if book does not exist")
  @Test
  void shouldThrowExceptionWhenCallDeleteByIdIfBookDoesNotExist() {
    // given
    Long id = 1L;
    String message = String.format("The book with id = [%d] does not exist!", id);

    given(bookRepo.existsById(id)).willReturn(false);
    // then
    assertThatThrownBy(() -> underTest.deleteById(id))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(message);
  }

  @DisplayName("Should call \"deleteByName()\" if book exist")
  @Test
  void shouldCallDeleteByNameIfBookExist() {
    // given
    String name = "Some name";
    given(bookRepo.existsByName(name)).willReturn(true);
    // when
    underTest.deleteByName(name);
    // then
    then(bookRepo).should().existsByName(name);
    then(bookRepo).should().deleteByName(name);
  }

  @DisplayName("Should throw exception when call \"deleteByName()\" if book does not exist")
  @Test
  void shouldThrowExceptionWhenCallDeleteByNameIfBookDoesNotExist() {
    // given
    String name = "Some name";
    String message = String.format("The book with name = [%s] does not exist!", name);


    given(bookRepo.existsByName(name)).willReturn(false);
    // then
    assertThatThrownBy(() -> underTest.deleteByName(name))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(message);
  }
}
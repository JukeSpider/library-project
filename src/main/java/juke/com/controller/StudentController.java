package juke.com.controller;

import java.util.List;
import juke.com.dto.StudentDto;
import juke.com.entity.StudentEntity;
import juke.com.mapper.IStudentMapper;
import juke.com.service.IStudentService;
import juke.com.validator.IStudentValidator;
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
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(path = "/feed")
@RestController
public class StudentController {

  private final IStudentService service;
  private final IStudentMapper mapper;
  private final IStudentValidator validator;

  @GetMapping("/all")
  public ResponseEntity<Page<StudentDto>> findAll(
      @PageableDefault(sort = {"id"}) Pageable pageable
  ) {
    Page<StudentEntity> studentEntityPage = service.findAll(pageable);
    List<StudentDto> studentDtoList = mapper.toDtoList(studentEntityPage.getContent());

    return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(studentDtoList));
  }

  @GetMapping("/all/age/{age}")
  public ResponseEntity<Page<StudentDto>> findAllByAge(
      @PathVariable Integer age,
      @PageableDefault(sort = {"id"}) Pageable pageable
  ) {
    Page<StudentEntity> studentEntityPage = service.findAllByAge(age, pageable);
    List<StudentDto> studentDtoList = mapper.toDtoList(studentEntityPage.getContent());

    return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(studentDtoList));
  }

  @GetMapping("/all/gender/{gender}")
  public ResponseEntity<Page<StudentDto>> findAllByGender(
      @PathVariable String gender,
      @PageableDefault(sort = {"id"}) Pageable pageable
  ) {
    validator.isValidGender(gender);

    Page<StudentEntity> studentEntityPage = service.findAllByGender(gender.toUpperCase(), pageable);
    List<StudentDto> studentDtoList = mapper.toDtoList(studentEntityPage.getContent());

    return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(studentDtoList));
  }

  @GetMapping("/all/book/{bookId}")
  public ResponseEntity<Page<StudentDto>> findAllByBookId(
      @PathVariable Long bookId,
      @PageableDefault(sort = {"id"}) Pageable pageable
  ) {
    validator.isValidId(bookId);

    Page<StudentEntity> studentEntityPage = service.findAllByBookId(bookId, pageable);
    List<StudentDto> studentDtoList = mapper.toDtoList(studentEntityPage.getContent());

    return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(studentDtoList));
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<StudentDto> findById(@PathVariable Long id) {

    validator.isValidId(id);

    StudentEntity studentEntity = service.findById(id);
    return ResponseEntity.status(HttpStatus.OK).body(mapper.toDto(studentEntity));
  }

  @GetMapping("/name/{studentName}")
  public ResponseEntity<StudentDto> findByName(@PathVariable String studentName) {

    String name = String.join(" ", studentName.split("_"));

    validator.isValidName(name);

    StudentEntity studentEntity = service.findByName(name);
    return ResponseEntity.status(HttpStatus.OK).body(mapper.toDto(studentEntity));
  }

  @GetMapping("/phone/{phone}")
  public ResponseEntity<StudentDto> findByPhone(@PathVariable String phone) {

    validator.isValidPhone(phone);

    StudentEntity studentEntity = service.findByPhone(phone);
    return ResponseEntity.status(HttpStatus.OK).body(mapper.toDto(studentEntity));
  }

  @PostMapping("/save")
  public ResponseEntity<StudentDto> save(@RequestBody StudentDto studentDto) {

    validator.isValidStudent(studentDto);

    StudentEntity studentEntity = mapper.toEntity(studentDto);
    studentEntity = service.save(studentEntity);

    return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(studentEntity));
  }

  @PostMapping("/all/save")
  public ResponseEntity<List<StudentDto>> saveAll(@RequestBody List<StudentDto> studentDtoList) {

    validator.isValidStudentList(studentDtoList);

    List<StudentEntity> studentEntityList = mapper.toEntityList(studentDtoList);
    studentEntityList = service.saveAll(studentEntityList);

    return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDtoList(studentEntityList));
  }

  @PutMapping("/update/name/{id}")
  public ResponseEntity<StudentDto> updateName(@PathVariable Long id, @RequestBody String name) {

    validator.isValidId(id);
    validator.isValidName(name);

    StudentEntity studentEntity = service.updateName(id, name);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(mapper.toDto(studentEntity));
  }

  @PutMapping("/update/age/{id}")
  public ResponseEntity<StudentDto> updateAge(@PathVariable Long id, @RequestBody Integer age) {

    validator.isValidId(id);
    validator.isValidAge(age);

    StudentEntity studentEntity = service.updateAge(id, age);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(mapper.toDto(studentEntity));
  }

  @PutMapping("/update/phone/{id}")
  public ResponseEntity<StudentDto> updatePhone(@PathVariable Long id, @RequestBody String phone) {

    validator.isValidId(id);
    validator.isValidPhone(phone);

    StudentEntity studentEntity = service.updatePhone(id, phone);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(mapper.toDto(studentEntity));
  }

  @PutMapping("/update/gender/{id}")
  public ResponseEntity<StudentDto> updateGender(
      @PathVariable Long id,
      @RequestBody String gender
  ) {

    validator.isValidId(id);
    validator.isValidGender(gender);

    StudentEntity studentEntity = service.updateGender(id, gender.toUpperCase());
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(mapper.toDto(studentEntity));
  }

  @PutMapping("/add/{studentId}/book/{bookId}")
  public ResponseEntity<StudentDto> addBook(
      @PathVariable Long studentId,
      @PathVariable Long bookId
  ) {

    validator.isValidId(studentId);
    validator.isValidId(bookId);

    StudentEntity studentEntity = service.addBook(bookId, studentId);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(mapper.toDto(studentEntity));
  }

  @PutMapping("/remove/{studentId}/book/{bookId}")
  public ResponseEntity<StudentDto> removeBook(
      @PathVariable Long studentId,
      @PathVariable Long bookId
  ) {

    validator.isValidId(studentId);
    validator.isValidId(bookId);

    StudentEntity studentEntity = service.removeBook(bookId, studentId);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(mapper.toDto(studentEntity));
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
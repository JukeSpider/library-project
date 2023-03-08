package juke.com.validator;

import java.util.List;
import juke.com.dto.StudentDto;

public interface IStudentValidator {

  void isValidStudent(StudentDto studentDto);

  void isValidStudentList(List<StudentDto> studentDtoList);

  void isValidId(Long id);

  void isValidName(String name);

  void isValidAge(Integer age);

  void isValidPhone(String phone);

  void isValidGender(String gender);
}
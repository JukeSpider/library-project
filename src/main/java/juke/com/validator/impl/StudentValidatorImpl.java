package juke.com.validator.impl;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import juke.com.dto.BookDto;
import juke.com.dto.StudentDto;
import juke.com.entity.enums.GenderEnum;
import juke.com.validator.IStudentValidator;
import org.springframework.stereotype.Component;

@Component
public class StudentValidatorImpl implements IStudentValidator {

  @Override
  public void isValidStudent(StudentDto student) {

    if (student == null) {
      throw new IllegalArgumentException("Argument [studentDto] is null!");
    }

    isValidName(student.getName());
    isValidAge(student.getAge());
    isValidPhone(student.getPhone());
    isValidGender(student.getGender());
  }

  @Override
  public void isValidStudentList(List<StudentDto> studentList) {

    if (studentList == null || studentList.isEmpty()) {
      throw new IllegalArgumentException("Student list is empty!");
    }

    studentList.forEach(this::isValidStudent);
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

    String namePattern = "^[A-Z][a-z]{2,}$";

    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("The student's name cannot be empty!");
    }

    if (name.length() < 3 || name.length() > 20) {
      throw new IllegalArgumentException(
          "The length of the student name must be between 3 and 20 characters!");
    }

    if (!Pattern.compile(namePattern).matcher(name).matches()) {
      throw new IllegalArgumentException("The name must contain only Latin letters!");
    }
  }

  @Override
  public void isValidAge(Integer age) {
    if (age < 16 || age > 40) {
      throw new IllegalArgumentException("The student's age must be between 16 and 40 years old");
    }
  }

  @Override
  public void isValidPhone(String phone) {

    String phonePattern = "^\\+\\d{1,2}\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}$";

    if (phone == null || phone.isBlank()) {
      throw new IllegalArgumentException("Student's phone number cannot be empty!");
    }

    if (!Pattern.compile(phonePattern).matcher(phone).matches()) {
      throw new IllegalArgumentException("The phone number must match the following pattern:\n"
          + "+X/XX (XXX)XXX-XX-XX");
    }
  }

  @Override
  public void isValidGender(String gender) {
    boolean isNotValidGender = Arrays.stream(GenderEnum.values())
        .noneMatch(genderString -> genderString.toString().equals(gender.toUpperCase()));

    if (isNotValidGender) {
      throw new IllegalArgumentException("Non-existent gender!");
    }
  }
}
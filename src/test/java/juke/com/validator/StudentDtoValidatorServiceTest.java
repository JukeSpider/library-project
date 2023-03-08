package juke.com.validator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StudentDtoValidatorServiceTest {

}

//  private IStudentDtoValidatorService validator;
//
//  @BeforeEach
//  void beforeEach() {
//    validator = new StudentDtoValidatorServiceImpl();
//  }
//
//  @DisplayName("Should execute \"isValidStudentDto()\" without exceptions with valid studentDto")
//  @Test
//  void shouldExecuteIsValidStudentDtoWithoutExceptionsWithValidStudentDto() {
//    // given
//    StudentDto validStudentDto = new StudentDto("Bob", 24, "+7 (923) 739-11-22", "MALE");
//    // then
//    assertThatNoException().isThrownBy(() -> validator.isValidStudentDto(validStudentDto));
//  }
//
//  @DisplayName("Should throw exception when call \"isValidStudentDto()\" with empty studentDto")
//  @Test
//  void shouldThrowExceptionWhenCallIsValidStudentDtoWithEmptyStudentDto() {
//    // given
//    String exceptionMessage = "Argument [studentDto] is null!";
//    // then
//    assertThatThrownBy(() -> validator.isValidStudentDto(null))
//        .isInstanceOf(IllegalArgumentException.class)
//        .hasMessageContaining(exceptionMessage);
//  }
//
//  @DisplayName("Should execute \"isValidStudentDtoName()\" without exceptions with valid name")
//  @Test
//  void shouldExecuteIsValidStudentDtoNameWithoutExceptionsWithValidName() {
//    // given
//    String validName = "Herman";
//    // then
//    assertThatNoException().isThrownBy(() -> validator.isValidStudentDtoName(validName));
//  }
//
//  @DisplayName("Should throw exception when call \"isValidStudentDtoName()\" with empty name")
//  @Test
//  void shouldThrowExceptionWhenCallIsValidStudentDtoNameWithEmptyName() {
//    // given
//    String exceptionMessage = "The student's name cannot be empty!";
//    // then
//    assertThatThrownBy(() -> validator.isValidStudentDtoName(""))
//        .isInstanceOf(IllegalArgumentException.class)
//        .hasMessageContaining(exceptionMessage);
//  }
//
//  @DisplayName("Should throw exception when call \"isValidStudentDtoName()\" "
//      + "if length of the name out of range")
//  @Test
//  void shouldThrowExceptionWhenCallIsValidStudentDtoNameIfLengthOfTheNameOutOfRange() {
//    // given
//    String exceptionMessage = "The length of the name must be between 3 and 20 characters!";
//    String tooShortName = "A";
//    String tooLongName = "VaryVaryLongStudentName";
//    // then
//    assertThatThrownBy(() -> validator.isValidStudentDtoName(tooShortName))
//        .isInstanceOf(IllegalArgumentException.class)
//        .hasMessageContaining(exceptionMessage);
//
//    assertThatThrownBy(() -> validator.isValidStudentDtoName(tooLongName))
//        .isInstanceOf(IllegalArgumentException.class)
//        .hasMessageContaining(exceptionMessage);
//  }
//
//  @DisplayName("Should throw exception when call \"isValidStudentDtoName()\" with not valid name")
//  @ParameterizedTest
//  @ValueSource(strings = {"Андрей", "Lıam", "alex", "Bob2014", "Katty@mail", "Harry Potter"})
//  void shouldThrowExceptionWhenCallIsValidStudentDtoNameWithNotValidName(String wrongName) {
//    // given
//    String exceptionMessage = "The name must contain only Latin letters!";
//    // then
//    assertThatThrownBy(() -> validator.isValidStudentDtoName(wrongName))
//        .isInstanceOf(IllegalArgumentException.class)
//        .hasMessageContaining(exceptionMessage);
//  }
//
//  @DisplayName("Should execute \"isValidStudentDtoAge()\" without exceptions with valid age")
//  @Test
//  void shouldExecuteIsValidStudentDtoAgeWithoutExceptionsWithValidAge() {
//    // given
//    int validAge = 17;
//    // then
//    assertThatNoException().isThrownBy(() -> validator.isValidStudentDtoAge(validAge));
//  }
//
//  @DisplayName("Should throw exception when call \"isValidStudentDtoAge()\" if age out of range")
//  @ParameterizedTest
//  @ValueSource(ints = {-1, 0, 1, 15, 41})
//  void shouldThrowExceptionWhenCallIsValidStudentDtoAgeIfAgeOutOfRange(int age) {
//    // given
//    String exceptionMessage = "The student's age must be between 16 and 40 years old";
//    // then
//    assertThatThrownBy(() -> validator.isValidStudentDtoAge(age))
//        .isInstanceOf(IllegalArgumentException.class)
//        .hasMessageContaining(exceptionMessage);
//  }
//
//  @DisplayName("Should execute \"isValidStudentDtoPhone()\" without exceptions with valid phone")
//  @Test
//  void shouldExecuteIsValidStudentDtoPhoneWithoutExceptionsWithValidPhone() {
//    // given
//    String validPhone = "+7 (923) 739-11-22";
//    // then
//    assertThatNoException().isThrownBy(() -> validator.isValidStudentDtoPhone(validPhone));
//  }
//
//  @DisplayName("Should throw exception when call \"isValidStudentDtoPhone()\" with empty phone")
//  @Test
//  void shouldThrowExceptionWhenCallIsValidStudentDtoPhoneWithEmptyPhone() {
//    // given
//    String exceptionMessage = "Student's phone number cannot be empty!";
//    // then
//    assertThatThrownBy(() -> validator.isValidStudentDtoPhone(""))
//        .isInstanceOf(IllegalArgumentException.class)
//        .hasMessageContaining(exceptionMessage);
//
//    assertThatThrownBy(() -> validator.isValidStudentDtoPhone(null))
//        .isInstanceOf(IllegalArgumentException.class)
//        .hasMessageContaining(exceptionMessage);
//  }
//
//  @DisplayName("Should throw exception when call \"isValidStudentDtoPhone()\" if phone is not valid")
//  @ParameterizedTest
//  @ValueSource(strings = {
//      "88005553535",
//      "8 951 654 12 87",
//      "+9 (123) 312-41-AZ",
//      "+987 (128) 312-48-AZ",
//      "+5 (123) 3124198",
//      "+1 (987)163-46-11"
//  })
//  void shouldThrowExceptionWhenCallIsValidStudentDtoPhoneIfPhoneIsNotValid(String phone) {
//    // given
//    String exceptionMessage = "The phone number must match the following pattern:\n"
//        + "+X/XX (XXX) XXX-XX-XX";
//    // then
//    assertThatThrownBy(() -> validator.isValidStudentDtoPhone(phone))
//        .isInstanceOf(IllegalArgumentException.class)
//        .hasMessageContaining(exceptionMessage);
//  }
//
//  @DisplayName("Should execute \"isValidStudentDtoGender()\" without exceptions with valid gender")
//  @Test
//  void shouldExecuteIsValidStudentDtoGenderWithoutExceptionsWithValidGender() {
//    // given
//    String validGender = "MALE";
//    // then
//    assertThatNoException().isThrownBy(() -> validator.isValidStudentDtoGender(validGender));
//  }
//
//  @DisplayName("Should throw exception when call \"isValidStudentDtoGender()\" if gender is not valid")
//  @Test
//  void shouldThrowExceptionWhenCallIsValidStudentDtoGenderIfGenderIsNotValid() {
//    // given
//    String exceptionMessage = "Non-existent gender!";
//    String notValidGender = "SOME";
//    // then
//
//    assertThatThrownBy(() -> validator.isValidStudentDtoGender(notValidGender))
//        .isInstanceOf(IllegalArgumentException.class)
//        .hasMessageContaining(exceptionMessage);
//  }
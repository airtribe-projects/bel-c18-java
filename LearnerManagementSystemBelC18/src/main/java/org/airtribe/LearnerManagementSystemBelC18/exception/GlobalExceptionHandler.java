package org.airtribe.LearnerManagementSystemBelC18.exception;

import java.util.HashMap;
import java.util.Map;
import org.airtribe.LearnerManagementSystemBelC18.entity.MyErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DuplicateEmailException.class)
  public ResponseEntity<MyErrorResponse> handleDuplicateEmailException(DuplicateEmailException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(new MyErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value()));
  }

  @ExceptionHandler(LearnerNotFoundException.class)
  public ResponseEntity<MyErrorResponse> handleLearnerNotFoundException(LearnerNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new MyErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
  }

  @ExceptionHandler(CohortNotFoundException.class)
  public ResponseEntity<MyErrorResponse> handleCohortNotFoundException(CohortNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new MyErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
  }

  @ExceptionHandler(CourseNotFoundException.class)
  public ResponseEntity<MyErrorResponse> handleCourseNotFoundException(CourseNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new MyErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String errorMessage = error.getDefaultMessage();
      String fieldName = ((FieldError) error).getField();
      errors.put(fieldName, errorMessage);
    });

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(errors);

  }
}

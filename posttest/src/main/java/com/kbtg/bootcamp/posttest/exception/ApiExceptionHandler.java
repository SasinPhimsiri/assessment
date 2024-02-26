package com.kbtg.bootcamp.posttest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException notFoundException) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(notFoundException.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now());

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ApiExceptionResponse> handleNotFoundException(BadRequestException badRequestException) {
        ApiExceptionResponse errorResponse = new ApiExceptionResponse(badRequestException.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TicketNotFoundException.class})
    public ResponseEntity<ApiExceptionResponse> handleNotFoundException(TicketNotFoundException ticketNotFoundException) {
        ApiExceptionResponse errorResponse = new ApiExceptionResponse(ticketNotFoundException.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<Map<String, Object>> buildValidationErrorResponse(Errors errors) {
        Map<String, String> validationErrors = errors.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (message1, message2) -> message1 + "; " + message2));

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("message", validationErrors);
        responseBody.put("httpStatus", HttpStatus.BAD_REQUEST);
        responseBody.put("timestamp", ZonedDateTime.now());
        return ResponseEntity.badRequest().body(responseBody);
    }

}



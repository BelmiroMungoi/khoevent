package com.bbm.khoevent.exception.handler;

import com.bbm.khoevent.exception.BadRequestException;
import com.bbm.khoevent.exception.EntityNotFoundException;
import com.bbm.khoevent.exception.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class KhoEventExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;



    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        List<StandardError.FieldError> fieldErrors = new ArrayList<>();

        for (ObjectError objectError: ex.getAllErrors()) {
            String name = ((FieldError) objectError).getField();
            String msg = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
            fieldErrors.add(new StandardError.FieldError(name, msg));
        }

        StandardError standardError = new StandardError();
        standardError.setCode(httpStatus.value());
        standardError.setStatus(httpStatus);
        standardError.setTitle("Um ou mais campos estão inválidos, Por favor tente novamente");
        standardError.setTime(LocalDateTime.now());
        standardError.setPath(request.getContextPath());
        standardError.setFields(fieldErrors);


        return super.handleExceptionInternal(ex, standardError, headers, httpStatus, request);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardError standardError = new StandardError();
        standardError.setCode(status.value());
        standardError.setStatus(status);
        standardError.setTitle(ex.getMessage());
        standardError.setTime(LocalDateTime.now());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.badRequest()
                .body(standardError);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(BadRequestException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardError standardError = new StandardError();
        standardError.setCode(status.value());
        standardError.setStatus(status);
        standardError.setTitle(ex.getMessage());
        standardError.setTime(LocalDateTime.now());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.badRequest()
                .body(standardError);
    }
}

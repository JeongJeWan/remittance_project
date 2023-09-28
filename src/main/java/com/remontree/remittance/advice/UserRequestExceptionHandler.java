package com.remontree.remittance.advice;


import com.remontree.remittance.exception.*;
import com.remontree.remittance.exception.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * 사용자에 대한 Exception Advice.
 */
@Slf4j
@RestControllerAdvice
public class UserRequestExceptionHandler {

    /**
     * (404) 객체 조회 실패에 대한 예외.
     *
     * @param ex    조회 실패 예외
     * @return      에러 메시지 & 에러 상태 코드 & 발생 시간 반환
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Error> handlerUserNotFoundException(NotFoundException ex) {
        Error errorResponse = new Error(ex.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
        log.error(errorResponse.getError());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * (400) 검증 실패에 대한 예외.
     *
     * @param ex    검증 실패 예외
     * @return      에러 메시지 & 에러 상태 코드 & 발생 시간 반환
     */
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<Error> handleInsufficientBalanceException(InsufficientBalanceException ex) {
        Error errorResponse = new Error(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        log.error(errorResponse.getError());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * (400) 검증 실패에 대한 예외.
     *
     * @param ex    검증 실패 예외
     * @return      에러 메시지 & 에러 상태 코드 & 발생 시간 반환
     */
    @ExceptionHandler(LimitExceededException.class)
    public ResponseEntity<Error> handleLimitExceededException(LimitExceededException ex) {
        Error errorResponse = new Error(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        log.error(errorResponse.getError());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Error> runtimeExceptionHandle(RuntimeException e) {
        Error errorResponse = new Error("시스템에서 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        log.error(errorResponse.getError());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

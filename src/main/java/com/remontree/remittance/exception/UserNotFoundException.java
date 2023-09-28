package com.remontree.remittance.exception;

/**
 * 사용자가 존재하지 않을 때 발생하는 예외 처리 클래스
 */
public class UserNotFoundException extends NotFoundException{

    private final static String MESSAGE = "사용자";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}

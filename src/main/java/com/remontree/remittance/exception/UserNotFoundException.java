package com.remontree.remittance.exception;

public class UserNotFoundException extends NotFoundException{

    private final static String MESSAGE = "사용자";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}

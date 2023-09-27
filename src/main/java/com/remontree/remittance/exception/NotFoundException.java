package com.remontree.remittance.exception;

public abstract class NotFoundException extends RuntimeException {
    protected NotFoundException(String target) {
        super("존재하지 않는 " + target + "입니다");
    }
}

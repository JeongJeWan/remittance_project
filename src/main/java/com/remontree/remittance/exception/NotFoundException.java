package com.remontree.remittance.exception;

/**
 * 존재하지 않는 예외에 대한 공통 처리 클래스
 */
public abstract class NotFoundException extends RuntimeException {
    protected NotFoundException(String target) {
        super("존재하지 않는 " + target + "입니다");
    }
}

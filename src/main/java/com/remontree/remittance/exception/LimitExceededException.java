package com.remontree.remittance.exception;

/**
 * 송금 시 받는 사용자에 대한 최대 한도 금액이 넘어갈 때 발생하는 예외 처리 클래스
 */
public class LimitExceededException extends RuntimeException {

    public LimitExceededException(String message) {
        super(message);
    }
}

package com.remontree.remittance.exception;

/**
 * 송금 시 잔액 부족할 경우 발생되는 예외 처리 클래스
 */
public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(String message) {
        super(message);
    }
}

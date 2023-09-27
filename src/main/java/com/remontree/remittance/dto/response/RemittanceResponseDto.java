package com.remontree.remittance.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 송금 목록 조회에 대한 Dto
 */
@Getter
public class RemittanceResponseDto {

    private final String senderName;
    private final String receiverName;
    private final Integer amount;
    private final LocalDateTime createdAt;

    public RemittanceResponseDto(String senderName, String receiverName, Integer amount, LocalDateTime createdAt) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.amount = amount;
        this.createdAt = createdAt;
    }
}

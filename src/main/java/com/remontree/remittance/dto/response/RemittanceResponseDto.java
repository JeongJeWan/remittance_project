package com.remontree.remittance.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 송금 목록 조회 후, 사용자에게 보여주기 쉽게 변환되는 Dto
 */
@Getter
public class RemittanceResponseDto {

    // 보내는 사용자 이름
    private final String senderName;
    // 받는 사용자 이름
    private final String receiverName;
    // 송금된 금액
    private final Integer amount;
    // 송금된 날짜
    private final LocalDateTime createdAt;

    public RemittanceResponseDto(String senderName, String receiverName, Integer amount, LocalDateTime createdAt) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.amount = amount;
        this.createdAt = createdAt;
    }
}

package com.remontree.remittance.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 송금 목록 조회에 대한 Dto
 */
@Getter
public class SelectRemittanceResponseDto {

    private final Long senderId;
    private final Long receiverId;
    private final Integer amount;
    private final LocalDateTime createdAt;

    @QueryProjection
    public SelectRemittanceResponseDto(Long senderId, Long receiverId,
                                       Integer amount, LocalDateTime createdAt) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.createdAt = createdAt;
    }

}

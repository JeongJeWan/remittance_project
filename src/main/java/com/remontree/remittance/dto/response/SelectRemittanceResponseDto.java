package com.remontree.remittance.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 송금 목록 조회에 대한 Dto
 */
@Getter
public class SelectRemittanceResponseDto {

    // 보내는 사용자 아이디
    private final Long senderId;
    // 받는 사용자 아이디
    private final Long receiverId;
    // 송금된 금액
    private final Integer amount;
    // 송금된 날짜
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

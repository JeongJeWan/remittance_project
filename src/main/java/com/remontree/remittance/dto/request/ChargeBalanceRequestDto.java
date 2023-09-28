package com.remontree.remittance.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 금액 충전에 대한 Dto
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChargeBalanceRequestDto {

    // 충전할 금액
    @NotBlank
    private Integer balance;
}

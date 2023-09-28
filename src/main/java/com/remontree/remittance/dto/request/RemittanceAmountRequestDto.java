package com.remontree.remittance.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 송금에 대한 Dto
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RemittanceAmountRequestDto {

    // 송금에 대한 금액
    @NotBlank
    private Integer amount;
}

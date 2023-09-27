package com.remontree.remittance.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChargeBalanceRequestDto {

    @NotBlank
    private Integer balance;
}

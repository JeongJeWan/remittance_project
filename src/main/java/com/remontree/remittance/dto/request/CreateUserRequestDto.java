package com.remontree.remittance.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 사용자 생성에 대한 Dto
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateUserRequestDto {

    // 사용자 이름
    @NotBlank
    @Length(min = 1, max = 30)
    private String username;
    // 사용자 최대 한도
    @NotNull
    private Integer maxLimit;
}

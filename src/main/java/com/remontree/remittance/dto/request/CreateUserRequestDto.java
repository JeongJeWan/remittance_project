package com.remontree.remittance.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateUserRequestDto {

    @NotBlank
    @Length(min = 1, max = 30)
    private String username;
    @NotNull
    private Integer maxLimit;
}

package com.remontree.remittance.repository.remittance;

import com.remontree.remittance.dto.response.SelectRemittanceResponseDto;

import java.util.List;

public interface RemittanceRepositoryCustom {

    List<SelectRemittanceResponseDto> findUserRemittanceList(Long userId);
}

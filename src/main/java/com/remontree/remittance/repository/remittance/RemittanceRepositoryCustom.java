package com.remontree.remittance.repository.remittance;

import com.remontree.remittance.dto.response.SelectRemittanceResponseDto;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface RemittanceRepositoryCustom {

    /**
     * 특정 사용자에 대한 송금 목록 조회 메서드.
     *
     * @param userId    사용자 아이디
     * @return          사용자 송금 목록 반환
     */
    List<SelectRemittanceResponseDto> findUserRemittanceList(Long userId);
}

package com.remontree.remittance.repository.remittance;

import com.remontree.remittance.dto.response.QSelectRemittanceResponseDto;
import com.remontree.remittance.entity.QUser;

import com.remontree.remittance.dto.response.SelectRemittanceResponseDto;
import com.remontree.remittance.entity.Remittance;
import com.remontree.remittance.entity.QRemittance;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class RemittanceRepositoryImpl extends QuerydslRepositorySupport implements RemittanceRepositoryCustom {
    public RemittanceRepositoryImpl() {
        super(Remittance.class);
    }

    @Override
    public List<SelectRemittanceResponseDto> findUserRemittanceList(Long userId) {

        QUser user = QUser.user;
        QRemittance remittance = QRemittance.remittance;

        return from(remittance)
                .select(new QSelectRemittanceResponseDto(
                        remittance.sender.id, remittance.receiver.id,
                        remittance.amount, remittance.createdAt
                ))
                .where(remittance.sender.id.eq(userId))
                .fetch();
    }
}

package com.remontree.remittance.repository.remittance;

import com.remontree.remittance.dto.response.SelectRemittanceResponseDto;
import com.remontree.remittance.entity.Remittance;
import com.remontree.remittance.entity.User;
import com.remontree.remittance.entity.UserStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 송금에 대한 Repository 테스트.
 */
@Slf4j
@DataJpaTest
class RemittanceRepositoryTest {

    @Autowired
    private RemittanceRepository remittanceRepository;

    @Autowired
    TestEntityManager em;

    User senderUser;
    User receiverUser1;
    User receiverUser2;

    @BeforeEach
    void setup() {
        UserStatus userStatus = new UserStatus("ACTIVE", "활성");
        senderUser = new User(userStatus, "정제완", 500000, 2000000);
        receiverUser1 = new User(userStatus, "홍길동", 50000, 2000000);
        receiverUser2 = new User(userStatus, "김철수", 50000, 2000000);

        em.persist(userStatus);
        em.persist(senderUser);
        em.persist(receiverUser1);
        em.persist(receiverUser2);
    }

    @Test
    @DisplayName("사용자가 다른 사용자에게 송금 시 성공")
    void createRemittance() {
        Remittance remittance = new Remittance(senderUser, receiverUser1, 30000);

        Remittance actual = remittanceRepository.save(remittance);

        assertEquals(senderUser.getUsername(), actual.getSender().getUsername());
        assertEquals(receiverUser1.getUsername(), actual.getReceiver().getUsername());
    }

    @Test
    @DisplayName("사용자 송금에 대한 목록 조회 성공")
    void findUserRemittanceList() {
        Remittance remittance1 = new Remittance(senderUser, receiverUser1, 30000);
        Remittance remittance2 = new Remittance(senderUser, receiverUser2, 30000);

        remittanceRepository.save(remittance1);
        remittanceRepository.save(remittance2);

        List<SelectRemittanceResponseDto> responseDtoList = remittanceRepository.findUserRemittanceList(senderUser.getId());

        assertNotNull(responseDtoList);
        assertEquals(remittance1.getSender().getId(), responseDtoList.get(0).getSenderId());
        assertEquals(remittance1.getReceiver().getId(), responseDtoList.get(0).getReceiverId());
        assertEquals(remittance1.getAmount(), responseDtoList.get(0).getAmount());
    }
}
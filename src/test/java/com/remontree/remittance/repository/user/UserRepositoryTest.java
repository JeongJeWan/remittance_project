package com.remontree.remittance.repository.user;

import com.remontree.remittance.entity.User;
import com.remontree.remittance.entity.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager em;

    User user;
    @BeforeEach
    void setup() {
        UserStatus userStatus = new UserStatus("ACTIVE", "활성");
        user = new User(userStatus, "정제완", 500000, 2000000);

        em.persist(userStatus);
        em.persist(user);
    }

    @Test
    @DisplayName("사용자 아이디를 통해 사용자 이름 조회하기")
    void findUsernameById() {
        String actual = userRepository.findUsernameById(user.getId());

        assertEquals(user.getUsername(), actual);
    }
}
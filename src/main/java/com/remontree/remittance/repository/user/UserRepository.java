package com.remontree.remittance.repository.user;

import com.remontree.remittance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 사용자 CRU를 위한 Repository
 */
public interface UserRepository extends JpaRepository<User, Long> {

    String findUsernameById(Long userId);
}

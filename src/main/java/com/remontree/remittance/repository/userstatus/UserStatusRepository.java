package com.remontree.remittance.repository.userstatus;

import com.remontree.remittance.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusRepository extends JpaRepository<UserStatus, String> {
}

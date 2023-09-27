package com.remontree.remittance.repository.remittance;

import com.remontree.remittance.entity.Remittance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemittanceRepository extends JpaRepository<Remittance, Long> {
}

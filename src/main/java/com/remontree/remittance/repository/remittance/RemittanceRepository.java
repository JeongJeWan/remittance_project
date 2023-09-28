package com.remontree.remittance.repository.remittance;

import com.remontree.remittance.entity.Remittance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 송금에 대한  Repository
 */
public interface RemittanceRepository extends JpaRepository<Remittance, Long>, RemittanceRepositoryCustom {
}

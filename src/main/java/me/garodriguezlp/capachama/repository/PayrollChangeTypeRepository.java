package me.garodriguezlp.capachama.repository;

import me.garodriguezlp.capachama.domain.PayrollChangeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PayrollChangeType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayrollChangeTypeRepository extends JpaRepository<PayrollChangeType, Long> {}

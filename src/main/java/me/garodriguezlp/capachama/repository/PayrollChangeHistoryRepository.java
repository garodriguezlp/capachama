package me.garodriguezlp.capachama.repository;

import me.garodriguezlp.capachama.domain.PayrollChangeHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PayrollChangeHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayrollChangeHistoryRepository extends JpaRepository<PayrollChangeHistory, Long> {}

package me.garodriguezlp.capachama.service;

import java.util.Optional;
import me.garodriguezlp.capachama.service.dto.PayrollChangeHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link me.garodriguezlp.capachama.domain.PayrollChangeHistory}.
 */
public interface PayrollChangeHistoryService {
    /**
     * Save a payrollChangeHistory.
     *
     * @param payrollChangeHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    PayrollChangeHistoryDTO save(PayrollChangeHistoryDTO payrollChangeHistoryDTO);

    /**
     * Partially updates a payrollChangeHistory.
     *
     * @param payrollChangeHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PayrollChangeHistoryDTO> partialUpdate(PayrollChangeHistoryDTO payrollChangeHistoryDTO);

    /**
     * Get all the payrollChangeHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PayrollChangeHistoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" payrollChangeHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PayrollChangeHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" payrollChangeHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

package me.garodriguezlp.capachama.service;

import java.util.Optional;
import me.garodriguezlp.capachama.service.dto.PayrollChangeTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link me.garodriguezlp.capachama.domain.PayrollChangeType}.
 */
public interface PayrollChangeTypeService {
    /**
     * Save a payrollChangeType.
     *
     * @param payrollChangeTypeDTO the entity to save.
     * @return the persisted entity.
     */
    PayrollChangeTypeDTO save(PayrollChangeTypeDTO payrollChangeTypeDTO);

    /**
     * Partially updates a payrollChangeType.
     *
     * @param payrollChangeTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PayrollChangeTypeDTO> partialUpdate(PayrollChangeTypeDTO payrollChangeTypeDTO);

    /**
     * Get all the payrollChangeTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PayrollChangeTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" payrollChangeType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PayrollChangeTypeDTO> findOne(Long id);

    /**
     * Delete the "id" payrollChangeType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

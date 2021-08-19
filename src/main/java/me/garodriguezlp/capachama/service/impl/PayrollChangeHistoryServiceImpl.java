package me.garodriguezlp.capachama.service.impl;

import java.util.Optional;
import me.garodriguezlp.capachama.domain.PayrollChangeHistory;
import me.garodriguezlp.capachama.repository.PayrollChangeHistoryRepository;
import me.garodriguezlp.capachama.service.PayrollChangeHistoryService;
import me.garodriguezlp.capachama.service.dto.PayrollChangeHistoryDTO;
import me.garodriguezlp.capachama.service.mapper.PayrollChangeHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PayrollChangeHistory}.
 */
@Service
@Transactional
public class PayrollChangeHistoryServiceImpl implements PayrollChangeHistoryService {

    private final Logger log = LoggerFactory.getLogger(PayrollChangeHistoryServiceImpl.class);

    private final PayrollChangeHistoryRepository payrollChangeHistoryRepository;

    private final PayrollChangeHistoryMapper payrollChangeHistoryMapper;

    public PayrollChangeHistoryServiceImpl(
        PayrollChangeHistoryRepository payrollChangeHistoryRepository,
        PayrollChangeHistoryMapper payrollChangeHistoryMapper
    ) {
        this.payrollChangeHistoryRepository = payrollChangeHistoryRepository;
        this.payrollChangeHistoryMapper = payrollChangeHistoryMapper;
    }

    @Override
    public PayrollChangeHistoryDTO save(PayrollChangeHistoryDTO payrollChangeHistoryDTO) {
        log.debug("Request to save PayrollChangeHistory : {}", payrollChangeHistoryDTO);
        PayrollChangeHistory payrollChangeHistory = payrollChangeHistoryMapper.toEntity(payrollChangeHistoryDTO);
        payrollChangeHistory = payrollChangeHistoryRepository.save(payrollChangeHistory);
        return payrollChangeHistoryMapper.toDto(payrollChangeHistory);
    }

    @Override
    public Optional<PayrollChangeHistoryDTO> partialUpdate(PayrollChangeHistoryDTO payrollChangeHistoryDTO) {
        log.debug("Request to partially update PayrollChangeHistory : {}", payrollChangeHistoryDTO);

        return payrollChangeHistoryRepository
            .findById(payrollChangeHistoryDTO.getId())
            .map(
                existingPayrollChangeHistory -> {
                    payrollChangeHistoryMapper.partialUpdate(existingPayrollChangeHistory, payrollChangeHistoryDTO);

                    return existingPayrollChangeHistory;
                }
            )
            .map(payrollChangeHistoryRepository::save)
            .map(payrollChangeHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PayrollChangeHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PayrollChangeHistories");
        return payrollChangeHistoryRepository.findAll(pageable).map(payrollChangeHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PayrollChangeHistoryDTO> findOne(Long id) {
        log.debug("Request to get PayrollChangeHistory : {}", id);
        return payrollChangeHistoryRepository.findById(id).map(payrollChangeHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PayrollChangeHistory : {}", id);
        payrollChangeHistoryRepository.deleteById(id);
    }
}

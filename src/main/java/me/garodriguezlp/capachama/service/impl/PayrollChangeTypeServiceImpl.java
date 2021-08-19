package me.garodriguezlp.capachama.service.impl;

import java.util.Optional;
import me.garodriguezlp.capachama.domain.PayrollChangeType;
import me.garodriguezlp.capachama.repository.PayrollChangeTypeRepository;
import me.garodriguezlp.capachama.service.PayrollChangeTypeService;
import me.garodriguezlp.capachama.service.dto.PayrollChangeTypeDTO;
import me.garodriguezlp.capachama.service.mapper.PayrollChangeTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PayrollChangeType}.
 */
@Service
@Transactional
public class PayrollChangeTypeServiceImpl implements PayrollChangeTypeService {

    private final Logger log = LoggerFactory.getLogger(PayrollChangeTypeServiceImpl.class);

    private final PayrollChangeTypeRepository payrollChangeTypeRepository;

    private final PayrollChangeTypeMapper payrollChangeTypeMapper;

    public PayrollChangeTypeServiceImpl(
        PayrollChangeTypeRepository payrollChangeTypeRepository,
        PayrollChangeTypeMapper payrollChangeTypeMapper
    ) {
        this.payrollChangeTypeRepository = payrollChangeTypeRepository;
        this.payrollChangeTypeMapper = payrollChangeTypeMapper;
    }

    @Override
    public PayrollChangeTypeDTO save(PayrollChangeTypeDTO payrollChangeTypeDTO) {
        log.debug("Request to save PayrollChangeType : {}", payrollChangeTypeDTO);
        PayrollChangeType payrollChangeType = payrollChangeTypeMapper.toEntity(payrollChangeTypeDTO);
        payrollChangeType = payrollChangeTypeRepository.save(payrollChangeType);
        return payrollChangeTypeMapper.toDto(payrollChangeType);
    }

    @Override
    public Optional<PayrollChangeTypeDTO> partialUpdate(PayrollChangeTypeDTO payrollChangeTypeDTO) {
        log.debug("Request to partially update PayrollChangeType : {}", payrollChangeTypeDTO);

        return payrollChangeTypeRepository
            .findById(payrollChangeTypeDTO.getId())
            .map(
                existingPayrollChangeType -> {
                    payrollChangeTypeMapper.partialUpdate(existingPayrollChangeType, payrollChangeTypeDTO);

                    return existingPayrollChangeType;
                }
            )
            .map(payrollChangeTypeRepository::save)
            .map(payrollChangeTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PayrollChangeTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PayrollChangeTypes");
        return payrollChangeTypeRepository.findAll(pageable).map(payrollChangeTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PayrollChangeTypeDTO> findOne(Long id) {
        log.debug("Request to get PayrollChangeType : {}", id);
        return payrollChangeTypeRepository.findById(id).map(payrollChangeTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PayrollChangeType : {}", id);
        payrollChangeTypeRepository.deleteById(id);
    }
}

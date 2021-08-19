package me.garodriguezlp.capachama.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import me.garodriguezlp.capachama.repository.PayrollChangeTypeRepository;
import me.garodriguezlp.capachama.service.PayrollChangeTypeService;
import me.garodriguezlp.capachama.service.dto.PayrollChangeTypeDTO;
import me.garodriguezlp.capachama.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link me.garodriguezlp.capachama.domain.PayrollChangeType}.
 */
@RestController
@RequestMapping("/api")
public class PayrollChangeTypeResource {

    private final Logger log = LoggerFactory.getLogger(PayrollChangeTypeResource.class);

    private static final String ENTITY_NAME = "payrollChangeType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PayrollChangeTypeService payrollChangeTypeService;

    private final PayrollChangeTypeRepository payrollChangeTypeRepository;

    public PayrollChangeTypeResource(
        PayrollChangeTypeService payrollChangeTypeService,
        PayrollChangeTypeRepository payrollChangeTypeRepository
    ) {
        this.payrollChangeTypeService = payrollChangeTypeService;
        this.payrollChangeTypeRepository = payrollChangeTypeRepository;
    }

    /**
     * {@code POST  /payroll-change-types} : Create a new payrollChangeType.
     *
     * @param payrollChangeTypeDTO the payrollChangeTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new payrollChangeTypeDTO, or with status {@code 400 (Bad Request)} if the payrollChangeType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payroll-change-types")
    public ResponseEntity<PayrollChangeTypeDTO> createPayrollChangeType(@RequestBody PayrollChangeTypeDTO payrollChangeTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save PayrollChangeType : {}", payrollChangeTypeDTO);
        if (payrollChangeTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new payrollChangeType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PayrollChangeTypeDTO result = payrollChangeTypeService.save(payrollChangeTypeDTO);
        return ResponseEntity
            .created(new URI("/api/payroll-change-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payroll-change-types/:id} : Updates an existing payrollChangeType.
     *
     * @param id the id of the payrollChangeTypeDTO to save.
     * @param payrollChangeTypeDTO the payrollChangeTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payrollChangeTypeDTO,
     * or with status {@code 400 (Bad Request)} if the payrollChangeTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the payrollChangeTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payroll-change-types/{id}")
    public ResponseEntity<PayrollChangeTypeDTO> updatePayrollChangeType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PayrollChangeTypeDTO payrollChangeTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PayrollChangeType : {}, {}", id, payrollChangeTypeDTO);
        if (payrollChangeTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payrollChangeTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payrollChangeTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PayrollChangeTypeDTO result = payrollChangeTypeService.save(payrollChangeTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payrollChangeTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payroll-change-types/:id} : Partial updates given fields of an existing payrollChangeType, field will ignore if it is null
     *
     * @param id the id of the payrollChangeTypeDTO to save.
     * @param payrollChangeTypeDTO the payrollChangeTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payrollChangeTypeDTO,
     * or with status {@code 400 (Bad Request)} if the payrollChangeTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the payrollChangeTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the payrollChangeTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payroll-change-types/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PayrollChangeTypeDTO> partialUpdatePayrollChangeType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PayrollChangeTypeDTO payrollChangeTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PayrollChangeType partially : {}, {}", id, payrollChangeTypeDTO);
        if (payrollChangeTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payrollChangeTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payrollChangeTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PayrollChangeTypeDTO> result = payrollChangeTypeService.partialUpdate(payrollChangeTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payrollChangeTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /payroll-change-types} : get all the payrollChangeTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payrollChangeTypes in body.
     */
    @GetMapping("/payroll-change-types")
    public ResponseEntity<List<PayrollChangeTypeDTO>> getAllPayrollChangeTypes(Pageable pageable) {
        log.debug("REST request to get a page of PayrollChangeTypes");
        Page<PayrollChangeTypeDTO> page = payrollChangeTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payroll-change-types/:id} : get the "id" payrollChangeType.
     *
     * @param id the id of the payrollChangeTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the payrollChangeTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payroll-change-types/{id}")
    public ResponseEntity<PayrollChangeTypeDTO> getPayrollChangeType(@PathVariable Long id) {
        log.debug("REST request to get PayrollChangeType : {}", id);
        Optional<PayrollChangeTypeDTO> payrollChangeTypeDTO = payrollChangeTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(payrollChangeTypeDTO);
    }

    /**
     * {@code DELETE  /payroll-change-types/:id} : delete the "id" payrollChangeType.
     *
     * @param id the id of the payrollChangeTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payroll-change-types/{id}")
    public ResponseEntity<Void> deletePayrollChangeType(@PathVariable Long id) {
        log.debug("REST request to delete PayrollChangeType : {}", id);
        payrollChangeTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

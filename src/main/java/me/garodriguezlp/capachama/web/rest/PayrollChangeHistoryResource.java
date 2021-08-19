package me.garodriguezlp.capachama.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import me.garodriguezlp.capachama.repository.PayrollChangeHistoryRepository;
import me.garodriguezlp.capachama.service.PayrollChangeHistoryService;
import me.garodriguezlp.capachama.service.dto.PayrollChangeHistoryDTO;
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
 * REST controller for managing {@link me.garodriguezlp.capachama.domain.PayrollChangeHistory}.
 */
@RestController
@RequestMapping("/api")
public class PayrollChangeHistoryResource {

    private final Logger log = LoggerFactory.getLogger(PayrollChangeHistoryResource.class);

    private static final String ENTITY_NAME = "payrollChangeHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PayrollChangeHistoryService payrollChangeHistoryService;

    private final PayrollChangeHistoryRepository payrollChangeHistoryRepository;

    public PayrollChangeHistoryResource(
        PayrollChangeHistoryService payrollChangeHistoryService,
        PayrollChangeHistoryRepository payrollChangeHistoryRepository
    ) {
        this.payrollChangeHistoryService = payrollChangeHistoryService;
        this.payrollChangeHistoryRepository = payrollChangeHistoryRepository;
    }

    /**
     * {@code POST  /payroll-change-histories} : Create a new payrollChangeHistory.
     *
     * @param payrollChangeHistoryDTO the payrollChangeHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new payrollChangeHistoryDTO, or with status {@code 400 (Bad Request)} if the payrollChangeHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payroll-change-histories")
    public ResponseEntity<PayrollChangeHistoryDTO> createPayrollChangeHistory(@RequestBody PayrollChangeHistoryDTO payrollChangeHistoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save PayrollChangeHistory : {}", payrollChangeHistoryDTO);
        if (payrollChangeHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new payrollChangeHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PayrollChangeHistoryDTO result = payrollChangeHistoryService.save(payrollChangeHistoryDTO);
        return ResponseEntity
            .created(new URI("/api/payroll-change-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payroll-change-histories/:id} : Updates an existing payrollChangeHistory.
     *
     * @param id the id of the payrollChangeHistoryDTO to save.
     * @param payrollChangeHistoryDTO the payrollChangeHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payrollChangeHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the payrollChangeHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the payrollChangeHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payroll-change-histories/{id}")
    public ResponseEntity<PayrollChangeHistoryDTO> updatePayrollChangeHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PayrollChangeHistoryDTO payrollChangeHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PayrollChangeHistory : {}, {}", id, payrollChangeHistoryDTO);
        if (payrollChangeHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payrollChangeHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payrollChangeHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PayrollChangeHistoryDTO result = payrollChangeHistoryService.save(payrollChangeHistoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payrollChangeHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payroll-change-histories/:id} : Partial updates given fields of an existing payrollChangeHistory, field will ignore if it is null
     *
     * @param id the id of the payrollChangeHistoryDTO to save.
     * @param payrollChangeHistoryDTO the payrollChangeHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payrollChangeHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the payrollChangeHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the payrollChangeHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the payrollChangeHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payroll-change-histories/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PayrollChangeHistoryDTO> partialUpdatePayrollChangeHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PayrollChangeHistoryDTO payrollChangeHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PayrollChangeHistory partially : {}, {}", id, payrollChangeHistoryDTO);
        if (payrollChangeHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payrollChangeHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payrollChangeHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PayrollChangeHistoryDTO> result = payrollChangeHistoryService.partialUpdate(payrollChangeHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payrollChangeHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /payroll-change-histories} : get all the payrollChangeHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payrollChangeHistories in body.
     */
    @GetMapping("/payroll-change-histories")
    public ResponseEntity<List<PayrollChangeHistoryDTO>> getAllPayrollChangeHistories(Pageable pageable) {
        log.debug("REST request to get a page of PayrollChangeHistories");
        Page<PayrollChangeHistoryDTO> page = payrollChangeHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payroll-change-histories/:id} : get the "id" payrollChangeHistory.
     *
     * @param id the id of the payrollChangeHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the payrollChangeHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payroll-change-histories/{id}")
    public ResponseEntity<PayrollChangeHistoryDTO> getPayrollChangeHistory(@PathVariable Long id) {
        log.debug("REST request to get PayrollChangeHistory : {}", id);
        Optional<PayrollChangeHistoryDTO> payrollChangeHistoryDTO = payrollChangeHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(payrollChangeHistoryDTO);
    }

    /**
     * {@code DELETE  /payroll-change-histories/:id} : delete the "id" payrollChangeHistory.
     *
     * @param id the id of the payrollChangeHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payroll-change-histories/{id}")
    public ResponseEntity<Void> deletePayrollChangeHistory(@PathVariable Long id) {
        log.debug("REST request to delete PayrollChangeHistory : {}", id);
        payrollChangeHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

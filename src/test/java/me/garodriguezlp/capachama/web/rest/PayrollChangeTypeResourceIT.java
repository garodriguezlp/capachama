package me.garodriguezlp.capachama.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import me.garodriguezlp.capachama.IntegrationTest;
import me.garodriguezlp.capachama.domain.PayrollChangeType;
import me.garodriguezlp.capachama.repository.PayrollChangeTypeRepository;
import me.garodriguezlp.capachama.service.dto.PayrollChangeTypeDTO;
import me.garodriguezlp.capachama.service.mapper.PayrollChangeTypeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PayrollChangeTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PayrollChangeTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payroll-change-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PayrollChangeTypeRepository payrollChangeTypeRepository;

    @Autowired
    private PayrollChangeTypeMapper payrollChangeTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPayrollChangeTypeMockMvc;

    private PayrollChangeType payrollChangeType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PayrollChangeType createEntity(EntityManager em) {
        PayrollChangeType payrollChangeType = new PayrollChangeType().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return payrollChangeType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PayrollChangeType createUpdatedEntity(EntityManager em) {
        PayrollChangeType payrollChangeType = new PayrollChangeType().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return payrollChangeType;
    }

    @BeforeEach
    public void initTest() {
        payrollChangeType = createEntity(em);
    }

    @Test
    @Transactional
    void createPayrollChangeType() throws Exception {
        int databaseSizeBeforeCreate = payrollChangeTypeRepository.findAll().size();
        // Create the PayrollChangeType
        PayrollChangeTypeDTO payrollChangeTypeDTO = payrollChangeTypeMapper.toDto(payrollChangeType);
        restPayrollChangeTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payrollChangeTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PayrollChangeType in the database
        List<PayrollChangeType> payrollChangeTypeList = payrollChangeTypeRepository.findAll();
        assertThat(payrollChangeTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PayrollChangeType testPayrollChangeType = payrollChangeTypeList.get(payrollChangeTypeList.size() - 1);
        assertThat(testPayrollChangeType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPayrollChangeType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createPayrollChangeTypeWithExistingId() throws Exception {
        // Create the PayrollChangeType with an existing ID
        payrollChangeType.setId(1L);
        PayrollChangeTypeDTO payrollChangeTypeDTO = payrollChangeTypeMapper.toDto(payrollChangeType);

        int databaseSizeBeforeCreate = payrollChangeTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayrollChangeTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payrollChangeTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayrollChangeType in the database
        List<PayrollChangeType> payrollChangeTypeList = payrollChangeTypeRepository.findAll();
        assertThat(payrollChangeTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPayrollChangeTypes() throws Exception {
        // Initialize the database
        payrollChangeTypeRepository.saveAndFlush(payrollChangeType);

        // Get all the payrollChangeTypeList
        restPayrollChangeTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payrollChangeType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getPayrollChangeType() throws Exception {
        // Initialize the database
        payrollChangeTypeRepository.saveAndFlush(payrollChangeType);

        // Get the payrollChangeType
        restPayrollChangeTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, payrollChangeType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payrollChangeType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingPayrollChangeType() throws Exception {
        // Get the payrollChangeType
        restPayrollChangeTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPayrollChangeType() throws Exception {
        // Initialize the database
        payrollChangeTypeRepository.saveAndFlush(payrollChangeType);

        int databaseSizeBeforeUpdate = payrollChangeTypeRepository.findAll().size();

        // Update the payrollChangeType
        PayrollChangeType updatedPayrollChangeType = payrollChangeTypeRepository.findById(payrollChangeType.getId()).get();
        // Disconnect from session so that the updates on updatedPayrollChangeType are not directly saved in db
        em.detach(updatedPayrollChangeType);
        updatedPayrollChangeType.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        PayrollChangeTypeDTO payrollChangeTypeDTO = payrollChangeTypeMapper.toDto(updatedPayrollChangeType);

        restPayrollChangeTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, payrollChangeTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payrollChangeTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the PayrollChangeType in the database
        List<PayrollChangeType> payrollChangeTypeList = payrollChangeTypeRepository.findAll();
        assertThat(payrollChangeTypeList).hasSize(databaseSizeBeforeUpdate);
        PayrollChangeType testPayrollChangeType = payrollChangeTypeList.get(payrollChangeTypeList.size() - 1);
        assertThat(testPayrollChangeType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPayrollChangeType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingPayrollChangeType() throws Exception {
        int databaseSizeBeforeUpdate = payrollChangeTypeRepository.findAll().size();
        payrollChangeType.setId(count.incrementAndGet());

        // Create the PayrollChangeType
        PayrollChangeTypeDTO payrollChangeTypeDTO = payrollChangeTypeMapper.toDto(payrollChangeType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayrollChangeTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, payrollChangeTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payrollChangeTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayrollChangeType in the database
        List<PayrollChangeType> payrollChangeTypeList = payrollChangeTypeRepository.findAll();
        assertThat(payrollChangeTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPayrollChangeType() throws Exception {
        int databaseSizeBeforeUpdate = payrollChangeTypeRepository.findAll().size();
        payrollChangeType.setId(count.incrementAndGet());

        // Create the PayrollChangeType
        PayrollChangeTypeDTO payrollChangeTypeDTO = payrollChangeTypeMapper.toDto(payrollChangeType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayrollChangeTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payrollChangeTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayrollChangeType in the database
        List<PayrollChangeType> payrollChangeTypeList = payrollChangeTypeRepository.findAll();
        assertThat(payrollChangeTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPayrollChangeType() throws Exception {
        int databaseSizeBeforeUpdate = payrollChangeTypeRepository.findAll().size();
        payrollChangeType.setId(count.incrementAndGet());

        // Create the PayrollChangeType
        PayrollChangeTypeDTO payrollChangeTypeDTO = payrollChangeTypeMapper.toDto(payrollChangeType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayrollChangeTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payrollChangeTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PayrollChangeType in the database
        List<PayrollChangeType> payrollChangeTypeList = payrollChangeTypeRepository.findAll();
        assertThat(payrollChangeTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePayrollChangeTypeWithPatch() throws Exception {
        // Initialize the database
        payrollChangeTypeRepository.saveAndFlush(payrollChangeType);

        int databaseSizeBeforeUpdate = payrollChangeTypeRepository.findAll().size();

        // Update the payrollChangeType using partial update
        PayrollChangeType partialUpdatedPayrollChangeType = new PayrollChangeType();
        partialUpdatedPayrollChangeType.setId(payrollChangeType.getId());

        partialUpdatedPayrollChangeType.name(UPDATED_NAME);

        restPayrollChangeTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayrollChangeType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayrollChangeType))
            )
            .andExpect(status().isOk());

        // Validate the PayrollChangeType in the database
        List<PayrollChangeType> payrollChangeTypeList = payrollChangeTypeRepository.findAll();
        assertThat(payrollChangeTypeList).hasSize(databaseSizeBeforeUpdate);
        PayrollChangeType testPayrollChangeType = payrollChangeTypeList.get(payrollChangeTypeList.size() - 1);
        assertThat(testPayrollChangeType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPayrollChangeType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdatePayrollChangeTypeWithPatch() throws Exception {
        // Initialize the database
        payrollChangeTypeRepository.saveAndFlush(payrollChangeType);

        int databaseSizeBeforeUpdate = payrollChangeTypeRepository.findAll().size();

        // Update the payrollChangeType using partial update
        PayrollChangeType partialUpdatedPayrollChangeType = new PayrollChangeType();
        partialUpdatedPayrollChangeType.setId(payrollChangeType.getId());

        partialUpdatedPayrollChangeType.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restPayrollChangeTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayrollChangeType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayrollChangeType))
            )
            .andExpect(status().isOk());

        // Validate the PayrollChangeType in the database
        List<PayrollChangeType> payrollChangeTypeList = payrollChangeTypeRepository.findAll();
        assertThat(payrollChangeTypeList).hasSize(databaseSizeBeforeUpdate);
        PayrollChangeType testPayrollChangeType = payrollChangeTypeList.get(payrollChangeTypeList.size() - 1);
        assertThat(testPayrollChangeType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPayrollChangeType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingPayrollChangeType() throws Exception {
        int databaseSizeBeforeUpdate = payrollChangeTypeRepository.findAll().size();
        payrollChangeType.setId(count.incrementAndGet());

        // Create the PayrollChangeType
        PayrollChangeTypeDTO payrollChangeTypeDTO = payrollChangeTypeMapper.toDto(payrollChangeType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayrollChangeTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, payrollChangeTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payrollChangeTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayrollChangeType in the database
        List<PayrollChangeType> payrollChangeTypeList = payrollChangeTypeRepository.findAll();
        assertThat(payrollChangeTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPayrollChangeType() throws Exception {
        int databaseSizeBeforeUpdate = payrollChangeTypeRepository.findAll().size();
        payrollChangeType.setId(count.incrementAndGet());

        // Create the PayrollChangeType
        PayrollChangeTypeDTO payrollChangeTypeDTO = payrollChangeTypeMapper.toDto(payrollChangeType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayrollChangeTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payrollChangeTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayrollChangeType in the database
        List<PayrollChangeType> payrollChangeTypeList = payrollChangeTypeRepository.findAll();
        assertThat(payrollChangeTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPayrollChangeType() throws Exception {
        int databaseSizeBeforeUpdate = payrollChangeTypeRepository.findAll().size();
        payrollChangeType.setId(count.incrementAndGet());

        // Create the PayrollChangeType
        PayrollChangeTypeDTO payrollChangeTypeDTO = payrollChangeTypeMapper.toDto(payrollChangeType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayrollChangeTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payrollChangeTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PayrollChangeType in the database
        List<PayrollChangeType> payrollChangeTypeList = payrollChangeTypeRepository.findAll();
        assertThat(payrollChangeTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePayrollChangeType() throws Exception {
        // Initialize the database
        payrollChangeTypeRepository.saveAndFlush(payrollChangeType);

        int databaseSizeBeforeDelete = payrollChangeTypeRepository.findAll().size();

        // Delete the payrollChangeType
        restPayrollChangeTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, payrollChangeType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PayrollChangeType> payrollChangeTypeList = payrollChangeTypeRepository.findAll();
        assertThat(payrollChangeTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

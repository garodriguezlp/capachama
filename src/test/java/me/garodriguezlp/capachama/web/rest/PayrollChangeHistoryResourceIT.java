package me.garodriguezlp.capachama.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import me.garodriguezlp.capachama.IntegrationTest;
import me.garodriguezlp.capachama.domain.PayrollChangeHistory;
import me.garodriguezlp.capachama.repository.PayrollChangeHistoryRepository;
import me.garodriguezlp.capachama.service.dto.PayrollChangeHistoryDTO;
import me.garodriguezlp.capachama.service.mapper.PayrollChangeHistoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PayrollChangeHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PayrollChangeHistoryResourceIT {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payroll-change-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PayrollChangeHistoryRepository payrollChangeHistoryRepository;

    @Autowired
    private PayrollChangeHistoryMapper payrollChangeHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPayrollChangeHistoryMockMvc;

    private PayrollChangeHistory payrollChangeHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PayrollChangeHistory createEntity(EntityManager em) {
        PayrollChangeHistory payrollChangeHistory = new PayrollChangeHistory()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .comments(DEFAULT_COMMENTS);
        return payrollChangeHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PayrollChangeHistory createUpdatedEntity(EntityManager em) {
        PayrollChangeHistory payrollChangeHistory = new PayrollChangeHistory()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .comments(UPDATED_COMMENTS);
        return payrollChangeHistory;
    }

    @BeforeEach
    public void initTest() {
        payrollChangeHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createPayrollChangeHistory() throws Exception {
        int databaseSizeBeforeCreate = payrollChangeHistoryRepository.findAll().size();
        // Create the PayrollChangeHistory
        PayrollChangeHistoryDTO payrollChangeHistoryDTO = payrollChangeHistoryMapper.toDto(payrollChangeHistory);
        restPayrollChangeHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payrollChangeHistoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PayrollChangeHistory in the database
        List<PayrollChangeHistory> payrollChangeHistoryList = payrollChangeHistoryRepository.findAll();
        assertThat(payrollChangeHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        PayrollChangeHistory testPayrollChangeHistory = payrollChangeHistoryList.get(payrollChangeHistoryList.size() - 1);
        assertThat(testPayrollChangeHistory.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPayrollChangeHistory.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPayrollChangeHistory.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    void createPayrollChangeHistoryWithExistingId() throws Exception {
        // Create the PayrollChangeHistory with an existing ID
        payrollChangeHistory.setId(1L);
        PayrollChangeHistoryDTO payrollChangeHistoryDTO = payrollChangeHistoryMapper.toDto(payrollChangeHistory);

        int databaseSizeBeforeCreate = payrollChangeHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayrollChangeHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payrollChangeHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayrollChangeHistory in the database
        List<PayrollChangeHistory> payrollChangeHistoryList = payrollChangeHistoryRepository.findAll();
        assertThat(payrollChangeHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPayrollChangeHistories() throws Exception {
        // Initialize the database
        payrollChangeHistoryRepository.saveAndFlush(payrollChangeHistory);

        // Get all the payrollChangeHistoryList
        restPayrollChangeHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payrollChangeHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }

    @Test
    @Transactional
    void getPayrollChangeHistory() throws Exception {
        // Initialize the database
        payrollChangeHistoryRepository.saveAndFlush(payrollChangeHistory);

        // Get the payrollChangeHistory
        restPayrollChangeHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, payrollChangeHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payrollChangeHistory.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }

    @Test
    @Transactional
    void getNonExistingPayrollChangeHistory() throws Exception {
        // Get the payrollChangeHistory
        restPayrollChangeHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPayrollChangeHistory() throws Exception {
        // Initialize the database
        payrollChangeHistoryRepository.saveAndFlush(payrollChangeHistory);

        int databaseSizeBeforeUpdate = payrollChangeHistoryRepository.findAll().size();

        // Update the payrollChangeHistory
        PayrollChangeHistory updatedPayrollChangeHistory = payrollChangeHistoryRepository.findById(payrollChangeHistory.getId()).get();
        // Disconnect from session so that the updates on updatedPayrollChangeHistory are not directly saved in db
        em.detach(updatedPayrollChangeHistory);
        updatedPayrollChangeHistory.startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE).comments(UPDATED_COMMENTS);
        PayrollChangeHistoryDTO payrollChangeHistoryDTO = payrollChangeHistoryMapper.toDto(updatedPayrollChangeHistory);

        restPayrollChangeHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, payrollChangeHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payrollChangeHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the PayrollChangeHistory in the database
        List<PayrollChangeHistory> payrollChangeHistoryList = payrollChangeHistoryRepository.findAll();
        assertThat(payrollChangeHistoryList).hasSize(databaseSizeBeforeUpdate);
        PayrollChangeHistory testPayrollChangeHistory = payrollChangeHistoryList.get(payrollChangeHistoryList.size() - 1);
        assertThat(testPayrollChangeHistory.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPayrollChangeHistory.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPayrollChangeHistory.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void putNonExistingPayrollChangeHistory() throws Exception {
        int databaseSizeBeforeUpdate = payrollChangeHistoryRepository.findAll().size();
        payrollChangeHistory.setId(count.incrementAndGet());

        // Create the PayrollChangeHistory
        PayrollChangeHistoryDTO payrollChangeHistoryDTO = payrollChangeHistoryMapper.toDto(payrollChangeHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayrollChangeHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, payrollChangeHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payrollChangeHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayrollChangeHistory in the database
        List<PayrollChangeHistory> payrollChangeHistoryList = payrollChangeHistoryRepository.findAll();
        assertThat(payrollChangeHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPayrollChangeHistory() throws Exception {
        int databaseSizeBeforeUpdate = payrollChangeHistoryRepository.findAll().size();
        payrollChangeHistory.setId(count.incrementAndGet());

        // Create the PayrollChangeHistory
        PayrollChangeHistoryDTO payrollChangeHistoryDTO = payrollChangeHistoryMapper.toDto(payrollChangeHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayrollChangeHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payrollChangeHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayrollChangeHistory in the database
        List<PayrollChangeHistory> payrollChangeHistoryList = payrollChangeHistoryRepository.findAll();
        assertThat(payrollChangeHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPayrollChangeHistory() throws Exception {
        int databaseSizeBeforeUpdate = payrollChangeHistoryRepository.findAll().size();
        payrollChangeHistory.setId(count.incrementAndGet());

        // Create the PayrollChangeHistory
        PayrollChangeHistoryDTO payrollChangeHistoryDTO = payrollChangeHistoryMapper.toDto(payrollChangeHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayrollChangeHistoryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payrollChangeHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PayrollChangeHistory in the database
        List<PayrollChangeHistory> payrollChangeHistoryList = payrollChangeHistoryRepository.findAll();
        assertThat(payrollChangeHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePayrollChangeHistoryWithPatch() throws Exception {
        // Initialize the database
        payrollChangeHistoryRepository.saveAndFlush(payrollChangeHistory);

        int databaseSizeBeforeUpdate = payrollChangeHistoryRepository.findAll().size();

        // Update the payrollChangeHistory using partial update
        PayrollChangeHistory partialUpdatedPayrollChangeHistory = new PayrollChangeHistory();
        partialUpdatedPayrollChangeHistory.setId(payrollChangeHistory.getId());

        partialUpdatedPayrollChangeHistory.startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE);

        restPayrollChangeHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayrollChangeHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayrollChangeHistory))
            )
            .andExpect(status().isOk());

        // Validate the PayrollChangeHistory in the database
        List<PayrollChangeHistory> payrollChangeHistoryList = payrollChangeHistoryRepository.findAll();
        assertThat(payrollChangeHistoryList).hasSize(databaseSizeBeforeUpdate);
        PayrollChangeHistory testPayrollChangeHistory = payrollChangeHistoryList.get(payrollChangeHistoryList.size() - 1);
        assertThat(testPayrollChangeHistory.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPayrollChangeHistory.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPayrollChangeHistory.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    void fullUpdatePayrollChangeHistoryWithPatch() throws Exception {
        // Initialize the database
        payrollChangeHistoryRepository.saveAndFlush(payrollChangeHistory);

        int databaseSizeBeforeUpdate = payrollChangeHistoryRepository.findAll().size();

        // Update the payrollChangeHistory using partial update
        PayrollChangeHistory partialUpdatedPayrollChangeHistory = new PayrollChangeHistory();
        partialUpdatedPayrollChangeHistory.setId(payrollChangeHistory.getId());

        partialUpdatedPayrollChangeHistory.startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE).comments(UPDATED_COMMENTS);

        restPayrollChangeHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayrollChangeHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayrollChangeHistory))
            )
            .andExpect(status().isOk());

        // Validate the PayrollChangeHistory in the database
        List<PayrollChangeHistory> payrollChangeHistoryList = payrollChangeHistoryRepository.findAll();
        assertThat(payrollChangeHistoryList).hasSize(databaseSizeBeforeUpdate);
        PayrollChangeHistory testPayrollChangeHistory = payrollChangeHistoryList.get(payrollChangeHistoryList.size() - 1);
        assertThat(testPayrollChangeHistory.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPayrollChangeHistory.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPayrollChangeHistory.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void patchNonExistingPayrollChangeHistory() throws Exception {
        int databaseSizeBeforeUpdate = payrollChangeHistoryRepository.findAll().size();
        payrollChangeHistory.setId(count.incrementAndGet());

        // Create the PayrollChangeHistory
        PayrollChangeHistoryDTO payrollChangeHistoryDTO = payrollChangeHistoryMapper.toDto(payrollChangeHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayrollChangeHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, payrollChangeHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payrollChangeHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayrollChangeHistory in the database
        List<PayrollChangeHistory> payrollChangeHistoryList = payrollChangeHistoryRepository.findAll();
        assertThat(payrollChangeHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPayrollChangeHistory() throws Exception {
        int databaseSizeBeforeUpdate = payrollChangeHistoryRepository.findAll().size();
        payrollChangeHistory.setId(count.incrementAndGet());

        // Create the PayrollChangeHistory
        PayrollChangeHistoryDTO payrollChangeHistoryDTO = payrollChangeHistoryMapper.toDto(payrollChangeHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayrollChangeHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payrollChangeHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayrollChangeHistory in the database
        List<PayrollChangeHistory> payrollChangeHistoryList = payrollChangeHistoryRepository.findAll();
        assertThat(payrollChangeHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPayrollChangeHistory() throws Exception {
        int databaseSizeBeforeUpdate = payrollChangeHistoryRepository.findAll().size();
        payrollChangeHistory.setId(count.incrementAndGet());

        // Create the PayrollChangeHistory
        PayrollChangeHistoryDTO payrollChangeHistoryDTO = payrollChangeHistoryMapper.toDto(payrollChangeHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayrollChangeHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payrollChangeHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PayrollChangeHistory in the database
        List<PayrollChangeHistory> payrollChangeHistoryList = payrollChangeHistoryRepository.findAll();
        assertThat(payrollChangeHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePayrollChangeHistory() throws Exception {
        // Initialize the database
        payrollChangeHistoryRepository.saveAndFlush(payrollChangeHistory);

        int databaseSizeBeforeDelete = payrollChangeHistoryRepository.findAll().size();

        // Delete the payrollChangeHistory
        restPayrollChangeHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, payrollChangeHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PayrollChangeHistory> payrollChangeHistoryList = payrollChangeHistoryRepository.findAll();
        assertThat(payrollChangeHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

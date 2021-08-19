package me.garodriguezlp.capachama.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import me.garodriguezlp.capachama.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PayrollChangeHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayrollChangeHistoryDTO.class);
        PayrollChangeHistoryDTO payrollChangeHistoryDTO1 = new PayrollChangeHistoryDTO();
        payrollChangeHistoryDTO1.setId(1L);
        PayrollChangeHistoryDTO payrollChangeHistoryDTO2 = new PayrollChangeHistoryDTO();
        assertThat(payrollChangeHistoryDTO1).isNotEqualTo(payrollChangeHistoryDTO2);
        payrollChangeHistoryDTO2.setId(payrollChangeHistoryDTO1.getId());
        assertThat(payrollChangeHistoryDTO1).isEqualTo(payrollChangeHistoryDTO2);
        payrollChangeHistoryDTO2.setId(2L);
        assertThat(payrollChangeHistoryDTO1).isNotEqualTo(payrollChangeHistoryDTO2);
        payrollChangeHistoryDTO1.setId(null);
        assertThat(payrollChangeHistoryDTO1).isNotEqualTo(payrollChangeHistoryDTO2);
    }
}

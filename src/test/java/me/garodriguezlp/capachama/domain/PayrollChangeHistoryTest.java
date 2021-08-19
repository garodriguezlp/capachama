package me.garodriguezlp.capachama.domain;

import static org.assertj.core.api.Assertions.assertThat;

import me.garodriguezlp.capachama.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PayrollChangeHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayrollChangeHistory.class);
        PayrollChangeHistory payrollChangeHistory1 = new PayrollChangeHistory();
        payrollChangeHistory1.setId(1L);
        PayrollChangeHistory payrollChangeHistory2 = new PayrollChangeHistory();
        payrollChangeHistory2.setId(payrollChangeHistory1.getId());
        assertThat(payrollChangeHistory1).isEqualTo(payrollChangeHistory2);
        payrollChangeHistory2.setId(2L);
        assertThat(payrollChangeHistory1).isNotEqualTo(payrollChangeHistory2);
        payrollChangeHistory1.setId(null);
        assertThat(payrollChangeHistory1).isNotEqualTo(payrollChangeHistory2);
    }
}

package me.garodriguezlp.capachama.domain;

import static org.assertj.core.api.Assertions.assertThat;

import me.garodriguezlp.capachama.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PayrollChangeTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayrollChangeType.class);
        PayrollChangeType payrollChangeType1 = new PayrollChangeType();
        payrollChangeType1.setId(1L);
        PayrollChangeType payrollChangeType2 = new PayrollChangeType();
        payrollChangeType2.setId(payrollChangeType1.getId());
        assertThat(payrollChangeType1).isEqualTo(payrollChangeType2);
        payrollChangeType2.setId(2L);
        assertThat(payrollChangeType1).isNotEqualTo(payrollChangeType2);
        payrollChangeType1.setId(null);
        assertThat(payrollChangeType1).isNotEqualTo(payrollChangeType2);
    }
}

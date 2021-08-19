package me.garodriguezlp.capachama.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import me.garodriguezlp.capachama.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PayrollChangeTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayrollChangeTypeDTO.class);
        PayrollChangeTypeDTO payrollChangeTypeDTO1 = new PayrollChangeTypeDTO();
        payrollChangeTypeDTO1.setId(1L);
        PayrollChangeTypeDTO payrollChangeTypeDTO2 = new PayrollChangeTypeDTO();
        assertThat(payrollChangeTypeDTO1).isNotEqualTo(payrollChangeTypeDTO2);
        payrollChangeTypeDTO2.setId(payrollChangeTypeDTO1.getId());
        assertThat(payrollChangeTypeDTO1).isEqualTo(payrollChangeTypeDTO2);
        payrollChangeTypeDTO2.setId(2L);
        assertThat(payrollChangeTypeDTO1).isNotEqualTo(payrollChangeTypeDTO2);
        payrollChangeTypeDTO1.setId(null);
        assertThat(payrollChangeTypeDTO1).isNotEqualTo(payrollChangeTypeDTO2);
    }
}

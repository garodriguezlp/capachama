package me.garodriguezlp.capachama.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PayrollChangeHistoryMapperTest {

    private PayrollChangeHistoryMapper payrollChangeHistoryMapper;

    @BeforeEach
    public void setUp() {
        payrollChangeHistoryMapper = new PayrollChangeHistoryMapperImpl();
    }
}

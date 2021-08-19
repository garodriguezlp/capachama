package me.garodriguezlp.capachama.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PayrollChangeTypeMapperTest {

    private PayrollChangeTypeMapper payrollChangeTypeMapper;

    @BeforeEach
    public void setUp() {
        payrollChangeTypeMapper = new PayrollChangeTypeMapperImpl();
    }
}

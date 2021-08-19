package me.garodriguezlp.capachama.service.mapper;

import me.garodriguezlp.capachama.domain.*;
import me.garodriguezlp.capachama.service.dto.PayrollChangeHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PayrollChangeHistory} and its DTO {@link PayrollChangeHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = { EmployeeMapper.class, ProjectMapper.class, PayrollChangeTypeMapper.class })
public interface PayrollChangeHistoryMapper extends EntityMapper<PayrollChangeHistoryDTO, PayrollChangeHistory> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "id")
    @Mapping(target = "manager", source = "manager", qualifiedByName = "id")
    @Mapping(target = "project", source = "project", qualifiedByName = "id")
    @Mapping(target = "changeType", source = "changeType", qualifiedByName = "id")
    PayrollChangeHistoryDTO toDto(PayrollChangeHistory s);
}

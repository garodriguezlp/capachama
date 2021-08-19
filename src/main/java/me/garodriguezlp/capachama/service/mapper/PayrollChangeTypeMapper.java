package me.garodriguezlp.capachama.service.mapper;

import me.garodriguezlp.capachama.domain.*;
import me.garodriguezlp.capachama.service.dto.PayrollChangeTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PayrollChangeType} and its DTO {@link PayrollChangeTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PayrollChangeTypeMapper extends EntityMapper<PayrollChangeTypeDTO, PayrollChangeType> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PayrollChangeTypeDTO toDtoId(PayrollChangeType payrollChangeType);
}

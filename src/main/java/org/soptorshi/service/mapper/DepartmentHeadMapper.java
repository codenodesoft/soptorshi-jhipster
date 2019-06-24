package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.DepartmentHeadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DepartmentHead and its DTO DepartmentHeadDTO.
 */
@Mapper(componentModel = "spring", uses = {DepartmentMapper.class, EmployeeMapper.class, OfficeMapper.class})
public interface DepartmentHeadMapper extends EntityMapper<DepartmentHeadDTO, DepartmentHead> {

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.fullName", target = "employeeFullName")
    @Mapping(source = "office.id", target = "officeId")
    @Mapping(source = "office.name", target = "officeName")
    DepartmentHeadDTO toDto(DepartmentHead departmentHead);

    @Mapping(source = "departmentId", target = "department")
    @Mapping(source = "employeeId", target = "employee")
    @Mapping(source = "officeId", target = "office")
    DepartmentHead toEntity(DepartmentHeadDTO departmentHeadDTO);

    default DepartmentHead fromId(Long id) {
        if (id == null) {
            return null;
        }
        DepartmentHead departmentHead = new DepartmentHead();
        departmentHead.setId(id);
        return departmentHead;
    }
}

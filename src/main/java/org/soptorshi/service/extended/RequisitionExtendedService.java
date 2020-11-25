package org.soptorshi.service.extended;

import org.soptorshi.domain.Employee;
import org.soptorshi.domain.enumeration.RequisitionStatus;
import org.soptorshi.mediator.RequisitionVoucherTransactionService;
import org.soptorshi.repository.RequisitionRepository;
import org.soptorshi.repository.extended.EmployeeExtendedRepository;
import org.soptorshi.repository.search.RequisitionSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.RequisitionService;
import org.soptorshi.service.dto.RequisitionDTO;
import org.soptorshi.service.mapper.RequisitionMapper;
import org.soptorshi.web.rest.errors.CustomParameterizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

@Service
@Transactional
public class RequisitionExtendedService extends RequisitionService {
    private final EmployeeExtendedRepository employeeExtendedRepository;
    private final RequisitionVoucherTransactionService requisitionVoucherTransactionService;

    public RequisitionExtendedService(RequisitionRepository requisitionRepository, RequisitionMapper requisitionMapper, RequisitionSearchRepository requisitionSearchRepository, EmployeeExtendedRepository employeeExtendedRepository, RequisitionVoucherTransactionService requisitionVoucherTransactionService) {
        super(requisitionRepository, requisitionMapper, requisitionSearchRepository);
        this.employeeExtendedRepository = employeeExtendedRepository;
        this.requisitionVoucherTransactionService = requisitionVoucherTransactionService;
    }

    @Override
    public RequisitionDTO save(RequisitionDTO requisitionDTO) {
        Employee employee = employeeExtendedRepository.findByEmployeeId(SecurityUtils.getCurrentUserLogin().get()).get();
        requisitionDTO.setDepartmentId(employee.getDepartment().getId());
        requisitionDTO.setOfficeId(employee.getOffice().getId());
        requisitionDTO.setEmployeeId(employee.getId());
        requisitionDTO = super.save(requisitionDTO);
        if(requisitionDTO.getStatus().equals(RequisitionStatus.RECEIVED_VERIFIED_BY_HEAD))
            requisitionVoucherTransactionService.createPaymentVoucher(requisitionDTO);
        return requisitionDTO;
    }
}

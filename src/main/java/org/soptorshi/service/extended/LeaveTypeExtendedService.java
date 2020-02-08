package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.LeaveType;
import org.soptorshi.repository.LeaveTypeRepository;
import org.soptorshi.repository.search.LeaveTypeSearchRepository;
import org.soptorshi.service.LeaveTypeService;
import org.soptorshi.service.dto.LeaveTypeDTO;
import org.soptorshi.service.mapper.LeaveTypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing LeaveType.
 */
@Service
@Transactional
public class LeaveTypeExtendedService extends LeaveTypeService {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeExtendedService.class);

    private final LeaveTypeRepository leaveTypeRepository;

    private final LeaveTypeMapper leaveTypeMapper;

    private final LeaveTypeSearchRepository leaveTypeSearchRepository;

    public LeaveTypeExtendedService(LeaveTypeRepository leaveTypeRepository, LeaveTypeMapper leaveTypeMapper, LeaveTypeSearchRepository leaveTypeSearchRepository) {
        super(leaveTypeRepository, leaveTypeMapper, leaveTypeSearchRepository);
        this.leaveTypeRepository = leaveTypeRepository;
        this.leaveTypeMapper = leaveTypeMapper;
        this.leaveTypeSearchRepository = leaveTypeSearchRepository;
    }

    /**
     * Save a leaveType.
     *
     * @param leaveTypeDTO the entity to save
     * @return the persisted entity
     */
    public LeaveTypeDTO save(LeaveTypeDTO leaveTypeDTO) {
        log.debug("Request to save LeaveType : {}", leaveTypeDTO);
        LeaveType leaveType = leaveTypeMapper.toEntity(leaveTypeDTO);
        leaveType = leaveTypeRepository.save(leaveType);
        LeaveTypeDTO result = leaveTypeMapper.toDto(leaveType);
        leaveTypeSearchRepository.save(leaveType);
        return result;
    }
}
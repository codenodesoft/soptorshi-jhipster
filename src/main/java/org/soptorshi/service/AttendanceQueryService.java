package org.soptorshi.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import org.soptorshi.domain.Attendance;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.AttendanceRepository;
import org.soptorshi.repository.search.AttendanceSearchRepository;
import org.soptorshi.service.dto.AttendanceCriteria;
import org.soptorshi.service.dto.AttendanceDTO;
import org.soptorshi.service.mapper.AttendanceMapper;

/**
 * Service for executing complex queries for Attendance entities in the database.
 * The main input is a {@link AttendanceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AttendanceDTO} or a {@link Page} of {@link AttendanceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AttendanceQueryService extends QueryService<Attendance> {

    private final Logger log = LoggerFactory.getLogger(AttendanceQueryService.class);

    private final AttendanceRepository attendanceRepository;

    private final AttendanceMapper attendanceMapper;

    private final AttendanceSearchRepository attendanceSearchRepository;

    public AttendanceQueryService(AttendanceRepository attendanceRepository, AttendanceMapper attendanceMapper, AttendanceSearchRepository attendanceSearchRepository) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceMapper = attendanceMapper;
        this.attendanceSearchRepository = attendanceSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AttendanceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AttendanceDTO> findByCriteria(AttendanceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Attendance> specification = createSpecification(criteria);
        return attendanceMapper.toDto(attendanceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AttendanceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AttendanceDTO> findByCriteria(AttendanceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Attendance> specification = createSpecification(criteria);
        return attendanceRepository.findAll(specification, page)
            .map(attendanceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AttendanceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Attendance> specification = createSpecification(criteria);
        return attendanceRepository.count(specification);
    }

    /**
     * Function to convert AttendanceCriteria to a {@link Specification}
     */
    private Specification<Attendance> createSpecification(AttendanceCriteria criteria) {
        Specification<Attendance> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Attendance_.id));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployeeId(), Attendance_.employeeId));
            }
            if (criteria.getAttendanceDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAttendanceDate(), Attendance_.attendanceDate));
            }
            if (criteria.getInTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInTime(), Attendance_.inTime));
            }
            if (criteria.getOutTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOutTime(), Attendance_.outTime));
            }
            if (criteria.getAttendanceExcelUploadId() != null) {
                specification = specification.and(buildSpecification(criteria.getAttendanceExcelUploadId(),
                    root -> root.join(Attendance_.attendanceExcelUpload, JoinType.LEFT).get(AttendanceExcelUpload_.id)));
            }
        }
        return specification;
    }
}
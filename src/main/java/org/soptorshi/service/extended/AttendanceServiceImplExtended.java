package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Attendance;
import org.soptorshi.domain.AttendanceExcelUpload;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.EmployeeRepository;
import org.soptorshi.repository.extended.AttendanceRepositoryExtended;
import org.soptorshi.repository.search.AttendanceSearchRepository;
import org.soptorshi.service.dto.AttendanceDTO;
import org.soptorshi.service.impl.AttendanceServiceImpl;
import org.soptorshi.service.mapper.AttendanceMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@Service
@Transactional
public class AttendanceServiceImplExtended extends AttendanceServiceImpl {

    private final Logger log = LoggerFactory.getLogger(AttendanceServiceImplExtended.class);

    private final AttendanceRepositoryExtended attendanceRepositoryExtended;

    private final AttendanceMapper attendanceMapper;

    private final AttendanceSearchRepository attendanceSearchRepository;

    private final EmployeeRepository employeeRepository;

    public AttendanceServiceImplExtended(AttendanceRepositoryExtended attendanceRepositoryExtended, AttendanceMapper attendanceMapper, AttendanceSearchRepository attendanceSearchRepository,
                                 EmployeeRepository employeeRepository) {
        super(attendanceRepositoryExtended, attendanceMapper, attendanceSearchRepository);
        this.attendanceRepositoryExtended = attendanceRepositoryExtended;
        this.attendanceMapper = attendanceMapper;
        this.attendanceSearchRepository = attendanceSearchRepository;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Save a attendance.
     *
     * @param attendanceDTO the entity to save
     * @return the persisted entity
     */

    public AttendanceDTO save(AttendanceDTO attendanceDTO) {
        log.debug("Request to save Attendance : {}", attendanceDTO);
        Optional<Employee> employee = employeeRepository.findByEmployeeId(attendanceDTO.getEmployeeId());
        if(employee.isPresent()) {
            Attendance attendance = attendanceMapper.toEntity(attendanceDTO);
            attendance = attendanceRepositoryExtended.save(attendance);
            AttendanceDTO result = attendanceMapper.toDto(attendance);
            attendanceSearchRepository.save(attendance);
            return result;
        }
        return null;
    }

    /**
     * Get all the attendances.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */

    @Transactional(readOnly = true)
    public Page<AttendanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Attendances");
        return attendanceRepositoryExtended.findAll(pageable)
            .map(attendanceMapper::toDto);
    }


    public List<AttendanceDTO> getAllByDistinctAttendanceDate() {
        log.debug("Request to get all Distinct Attendances Date");
        LocalDate localDate = LocalDate.now();
        List<Attendance> attendances = attendanceRepositoryExtended.getDistinctByAttendanceDateLessThanEqual(localDate);
        List<AttendanceDTO> attendanceDTOS = new ArrayList<>();
        for(Attendance attendance: attendances) {
            AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);
            attendanceDTOS.add(attendanceDTO);
        }
        return attendanceDTOS;
    }


    /**
     * Get one attendance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */

    @Transactional(readOnly = true)
    public Optional<AttendanceDTO> findOne(Long id) {
        log.debug("Request to get Attendance : {}", id);
        return attendanceRepositoryExtended.findById(id)
            .map(attendanceMapper::toDto);
    }

    /**
     * Delete the attendance by id.
     *
     * @param id the id of the entity
     */

    public void delete(Long id) {
        log.debug("Request to delete Attendance : {}", id);
        attendanceRepositoryExtended.deleteById(id);
        attendanceSearchRepository.deleteById(id);
    }


    public void deleteByAttendanceExcelUpload(AttendanceExcelUpload attendanceExcelUpload) {
        log.debug("Request to delete Attendance : {}", attendanceExcelUpload);
        List<Attendance> attendances = attendanceRepositoryExtended.getByAttendanceExcelUpload(attendanceExcelUpload);
        for(Attendance attendance: attendances) {
            attendanceRepositoryExtended.deleteById(attendance.getId());
            attendanceSearchRepository.deleteById(attendance.getId());
        }
    }

    /**
     * Search for the attendance corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */

    @Transactional(readOnly = true)
    public Page<AttendanceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Attendances for query {}", query);
        return attendanceSearchRepository.search(queryStringQuery(query), pageable)
            .map(attendanceMapper::toDto);
    }
}

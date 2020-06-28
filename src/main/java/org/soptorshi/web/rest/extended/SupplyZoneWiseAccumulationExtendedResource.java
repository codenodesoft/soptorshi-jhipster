package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyZoneWiseAccumulationQueryService;
import org.soptorshi.service.dto.SupplyZoneWiseAccumulationDTO;
import org.soptorshi.service.extended.SupplyZoneWiseAccumulationExtendedService;
import org.soptorshi.web.rest.SupplyZoneWiseAccumulationResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing SupplyZoneWiseAccumulation.
 */
@RestController
@RequestMapping("/api/extended")
public class SupplyZoneWiseAccumulationExtendedResource extends SupplyZoneWiseAccumulationResource {

    private final Logger log = LoggerFactory.getLogger(SupplyZoneWiseAccumulationExtendedResource.class);

    private static final String ENTITY_NAME = "supplyZoneWiseAccumulation";

    private final SupplyZoneWiseAccumulationExtendedService supplyZoneWiseAccumulationExtendedService;

    private final SupplyZoneWiseAccumulationQueryService supplyZoneWiseAccumulationQueryService;

    public SupplyZoneWiseAccumulationExtendedResource(SupplyZoneWiseAccumulationExtendedService supplyZoneWiseAccumulationExtendedService, SupplyZoneWiseAccumulationQueryService supplyZoneWiseAccumulationQueryService) {
        super(supplyZoneWiseAccumulationExtendedService,supplyZoneWiseAccumulationQueryService);
        this.supplyZoneWiseAccumulationExtendedService = supplyZoneWiseAccumulationExtendedService;
        this.supplyZoneWiseAccumulationQueryService = supplyZoneWiseAccumulationQueryService;
    }

    @PostMapping("/supply-zone-wise-accumulations")
    public ResponseEntity<SupplyZoneWiseAccumulationDTO> createSupplyZoneWiseAccumulation(@Valid @RequestBody SupplyZoneWiseAccumulationDTO supplyZoneWiseAccumulationDTO) throws URISyntaxException {
        log.debug("REST request to save SupplyZoneWiseAccumulation : {}", supplyZoneWiseAccumulationDTO);
        if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ZONE_MANAGER))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        if (supplyZoneWiseAccumulationDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplyZoneWiseAccumulation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplyZoneWiseAccumulationDTO result = supplyZoneWiseAccumulationExtendedService.save(supplyZoneWiseAccumulationDTO);
        return ResponseEntity.created(new URI("/api/supply-zone-wise-accumulations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/supply-zone-wise-accumulations")
    public ResponseEntity<SupplyZoneWiseAccumulationDTO> updateSupplyZoneWiseAccumulation(@Valid @RequestBody SupplyZoneWiseAccumulationDTO supplyZoneWiseAccumulationDTO) throws URISyntaxException {
        log.debug("REST request to update SupplyZoneWiseAccumulation : {}", supplyZoneWiseAccumulationDTO);
        if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ZONE_MANAGER))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        if (supplyZoneWiseAccumulationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplyZoneWiseAccumulationDTO result = supplyZoneWiseAccumulationExtendedService.save(supplyZoneWiseAccumulationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyZoneWiseAccumulationDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/supply-zone-wise-accumulations/{id}")
    public ResponseEntity<Void> deleteSupplyZoneWiseAccumulation(@PathVariable Long id) {
        log.debug("REST request to delete SupplyZoneWiseAccumulation : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}

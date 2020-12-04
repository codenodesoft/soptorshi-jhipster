package org.soptorshi.web.rest.extended;

import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.extended.CommercialReportService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/extended")
public class CommercialReportResource {
    private final Logger log = LoggerFactory.getLogger(CommercialReportResource.class);

    private final CommercialReportService commercialReportService;

    public CommercialReportResource(CommercialReportService commercialReportService) {
        this.commercialReportService = commercialReportService;
    }

    @GetMapping(value = "/commercial-budgets/report/all", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getCommercialBudgets() throws Exception, DocumentException {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", "commercialBudget", "invalidaccess");
        }
        ByteArrayInputStream byteArrayInputStream = commercialReportService.getCommercialBudgets();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/commercial-budget/report/all");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping(value = "/commercial-pis/report/commercialPi/{commercialPiNumber}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getCommercialPi(@PathVariable Long commercialPiNumber) throws Exception, DocumentException {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", "commercialBudget", "invalidaccess");
        }
        ByteArrayInputStream byteArrayInputStream = commercialReportService.getCommercialPi(commercialPiNumber);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/commercial-pis/report/commercialPi/{commercialPiNumber}");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping(value = "/commercial-pos/report/commercialPo/{commercialPoNumber}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getCommercialPo(@PathVariable Long commercialPoNumber) throws Exception, DocumentException {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", "commercialBudget", "invalidaccess");
        }
        ByteArrayInputStream byteArrayInputStream = commercialReportService.getCommercialPo(commercialPoNumber);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/commercial-pos/report/commercialPo/{commercialPoNumber}");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }
}

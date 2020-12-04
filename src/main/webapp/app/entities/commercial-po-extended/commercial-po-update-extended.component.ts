import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { CommercialPoExtendedService } from './commercial-po-extended.service';
import { CommercialPiService } from 'app/entities/commercial-pi';
import { CommercialPoUpdateComponent } from 'app/entities/commercial-po';

@Component({
    selector: 'jhi-commercial-po-update-extended',
    templateUrl: './commercial-po-update-extended.component.html'
})
export class CommercialPoUpdateExtendedComponent extends CommercialPoUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commercialPoService: CommercialPoExtendedService,
        protected commercialPiService: CommercialPiService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, commercialPoService, commercialPiService, activatedRoute);
    }

    generateReport() {
        if (this.commercialPo.id) {
            this.commercialPoService.generatePo(this.commercialPo.id);
        }
    }
}

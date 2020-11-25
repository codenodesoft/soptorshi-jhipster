import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IVendor } from 'app/shared/model/vendor.model';
import { VendorDetailComponent } from 'app/entities/vendor';

@Component({
    selector: 'jhi-vendor-extended-detail',
    templateUrl: './vendor-extended-detail.component.html'
})
export class VendorExtendedDetailComponent extends VendorDetailComponent {
    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
        super(dataUtils, activatedRoute);
    }
}

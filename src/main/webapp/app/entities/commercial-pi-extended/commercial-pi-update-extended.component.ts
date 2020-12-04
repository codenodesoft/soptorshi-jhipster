import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { CommercialPiExtendedService } from './commercial-pi-extended.service';
import { CommercialBudgetService } from 'app/entities/commercial-budget';
import { CommercialPiUpdateComponent } from 'app/entities/commercial-pi';
import { DATE_TIME_FORMAT } from 'app/shared';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ICommercialBudget } from 'app/shared/model/commercial-budget.model';
import { ICommercialProductInfo } from 'app/shared/model/commercial-product-info.model';
import { CommercialProductInfoService } from 'app/entities/commercial-product-info';

@Component({
    selector: 'jhi-commercial-pi-update-extended',
    templateUrl: './commercial-pi-update-extended.component.html'
})
export class CommercialPiUpdateExtendedComponent extends CommercialPiUpdateComponent {
    commercialProductInfos: ICommercialProductInfo[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commercialPiService: CommercialPiExtendedService,
        protected commercialBudgetService: CommercialBudgetService,
        protected activatedRoute: ActivatedRoute,
        protected commercialProductInfoService: CommercialProductInfoService
    ) {
        super(jhiAlertService, commercialPiService, commercialBudgetService, activatedRoute);
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commercialPi }) => {
            this.commercialPi = commercialPi;
            this.createdOn = this.commercialPi.createdOn != null ? this.commercialPi.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.commercialPi.updatedOn != null ? this.commercialPi.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
        this.commercialBudgetService
            .query({ 'commercialPiId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<ICommercialBudget[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommercialBudget[]>) => response.body)
            )
            .subscribe(
                (res: ICommercialBudget[]) => {
                    if (!this.commercialPi.commercialBudgetId) {
                        this.commercialbudgets = res;
                    } else {
                        this.commercialBudgetService
                            .find(this.commercialPi.commercialBudgetId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ICommercialBudget>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ICommercialBudget>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ICommercialBudget) => {
                                    this.commercialbudgets = [subRes].concat(res);
                                    this.getProductInfo();
                                },
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    getProductInfo() {
        this.commercialProductInfoService
            .query({
                'commercialBudgetId.equals': this.commercialbudgets[0].id == undefined ? 0 : this.commercialbudgets[0].id
            })
            .subscribe(
                (res: HttpResponse<ICommercialProductInfo[]>) => this.assignProductInfos(res.body, res.headers),
                (res: HttpErrorResponse) => 'error'
            );
    }

    protected assignProductInfos(data: ICommercialProductInfo[], headers: HttpHeaders) {
        this.commercialProductInfos = [];
        for (let i = 0; i < data.length; i++) {
            this.commercialProductInfos.push(data[i]);
        }
    }

    generateReport() {
        if (this.commercialPi.id) {
            this.commercialPiService.generatePi(this.commercialPi.id);
        }
    }
}

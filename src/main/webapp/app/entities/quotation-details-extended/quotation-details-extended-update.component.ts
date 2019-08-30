import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IQuotationDetails } from 'app/shared/model/quotation-details.model';
import { IQuotation } from 'app/shared/model/quotation.model';
import { QuotationService } from 'app/entities/quotation';
import { IRequisitionDetails } from 'app/shared/model/requisition-details.model';
import { RequisitionDetailsService } from 'app/entities/requisition-details';
import { QuotationDetailsService, QuotationDetailsUpdateComponent } from 'app/entities/quotation-details';
import { ProductService } from 'app/entities/product';
import { IProduct } from 'app/shared/model/product.model';
import { QuotationDetailsExtendedService } from 'app/entities/quotation-details-extended/quotation-details-extended.service';

@Component({
    selector: 'jhi-quotation-details-extended-update',
    templateUrl: './quotation-details-extended-update.component.html'
})
export class QuotationDetailsExtendedUpdateComponent extends QuotationDetailsUpdateComponent implements OnInit {
    quotation: IQuotation;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected quotationDetailsService: QuotationDetailsService,
        protected quotationService: QuotationService,
        protected requisitionDetailsService: RequisitionDetailsService,
        protected productService: ProductService,
        protected activatedRoute: ActivatedRoute,
        protected quotatinoDetailsExtendedService: QuotationDetailsExtendedService
    ) {
        super(
            dataUtils,
            jhiAlertService,
            quotationDetailsService,
            quotationService,
            requisitionDetailsService,
            productService,
            activatedRoute
        );
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ quotationDetails }) => {
            this.quotationDetails = quotationDetails;

            this.quotationService.find(this.quotationDetails.quotationId).subscribe((res: HttpResponse<IQuotation>) => {
                this.quotation = res.body;
                this.fetchProducts();
            });
        });
    }

    save() {
        this.isSaving = true;
        if (this.quotationDetails.id !== undefined) {
            this.subscribeToSaveResponse(this.quotatinoDetailsExtendedService.update(this.quotationDetails));
        } else {
            this.subscribeToSaveResponse(this.quotatinoDetailsExtendedService.create(this.quotationDetails));
        }
    }

    fetchProducts() {
        this.requisitionDetailsService
            .query({
                'requisitionId.equals': this.quotation.requisitionId
            })
            .subscribe((res: HttpResponse<IRequisitionDetails[]>) => {
                this.requisitiondetails = [];
                this.requisitiondetails = res.body;

                let productIdList: number[] = [];
                this.requisitiondetails.forEach((r: IRequisitionDetails) => {
                    productIdList.push(r.productId);
                });

                this.fetProductsContainsId(productIdList);
            });
    }

    fetProductsContainsId(productIdList: number[]) {
        this.productService
            .query({
                'id.in': productIdList.join(',')
            })
            .subscribe((res: HttpResponse<IProduct[]>) => {
                this.products = [];
                this.products = res.body;
            });
    }
}

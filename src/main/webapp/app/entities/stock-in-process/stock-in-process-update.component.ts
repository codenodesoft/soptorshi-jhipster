import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IStockInProcess } from 'app/shared/model/stock-in-process.model';
import { StockInProcessService } from './stock-in-process.service';
import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';
import { PurchaseOrderService } from 'app/entities/purchase-order';
import { ICommercialPurchaseOrder } from 'app/shared/model/commercial-purchase-order.model';
import { CommercialPurchaseOrderService } from 'app/entities/commercial-purchase-order';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';
import { IInventoryLocation } from 'app/shared/model/inventory-location.model';
import { InventoryLocationService } from 'app/entities/inventory-location';
import { IInventorySubLocation } from 'app/shared/model/inventory-sub-location.model';
import { InventorySubLocationService } from 'app/entities/inventory-sub-location';
import { IVendor } from 'app/shared/model/vendor.model';
import { VendorService } from 'app/entities/vendor';

@Component({
    selector: 'jhi-stock-in-process-update',
    templateUrl: './stock-in-process-update.component.html'
})
export class StockInProcessUpdateComponent implements OnInit {
    stockInProcess: IStockInProcess;
    isSaving: boolean;

    purchaseorders: IPurchaseOrder[];

    commercialpurchaseorders: ICommercialPurchaseOrder[];

    productcategories: IProductCategory[];

    products: IProduct[];

    inventorylocations: IInventoryLocation[];

    inventorysublocations: IInventorySubLocation[];

    vendors: IVendor[];
    mfgDateDp: any;
    expiryDateDp: any;
    processStartedOn: string;
    stockInDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected stockInProcessService: StockInProcessService,
        protected purchaseOrderService: PurchaseOrderService,
        protected commercialPurchaseOrderService: CommercialPurchaseOrderService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected inventoryLocationService: InventoryLocationService,
        protected inventorySubLocationService: InventorySubLocationService,
        protected vendorService: VendorService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stockInProcess }) => {
            this.stockInProcess = stockInProcess;
            this.processStartedOn =
                this.stockInProcess.processStartedOn != null ? this.stockInProcess.processStartedOn.format(DATE_TIME_FORMAT) : null;
            this.stockInDate = this.stockInProcess.stockInDate != null ? this.stockInProcess.stockInDate.format(DATE_TIME_FORMAT) : null;
        });
        this.purchaseOrderService
            .query({ 'stockInProcessId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<IPurchaseOrder[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPurchaseOrder[]>) => response.body)
            )
            .subscribe(
                (res: IPurchaseOrder[]) => {
                    if (!this.stockInProcess.purchaseOrderId) {
                        this.purchaseorders = res;
                    } else {
                        this.purchaseOrderService
                            .find(this.stockInProcess.purchaseOrderId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IPurchaseOrder>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IPurchaseOrder>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IPurchaseOrder) => (this.purchaseorders = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.commercialPurchaseOrderService
            .query({ 'stockInProcessId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<ICommercialPurchaseOrder[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommercialPurchaseOrder[]>) => response.body)
            )
            .subscribe(
                (res: ICommercialPurchaseOrder[]) => {
                    if (!this.stockInProcess.commercialPurchaseOrderId) {
                        this.commercialpurchaseorders = res;
                    } else {
                        this.commercialPurchaseOrderService
                            .find(this.stockInProcess.commercialPurchaseOrderId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ICommercialPurchaseOrder>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ICommercialPurchaseOrder>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ICommercialPurchaseOrder) => (this.commercialpurchaseorders = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.productCategoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductCategory[]>) => response.body)
            )
            .subscribe((res: IProductCategory[]) => (this.productcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProduct[]>) => response.body)
            )
            .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.inventoryLocationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IInventoryLocation[]>) => mayBeOk.ok),
                map((response: HttpResponse<IInventoryLocation[]>) => response.body)
            )
            .subscribe(
                (res: IInventoryLocation[]) => (this.inventorylocations = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.inventorySubLocationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IInventorySubLocation[]>) => mayBeOk.ok),
                map((response: HttpResponse<IInventorySubLocation[]>) => response.body)
            )
            .subscribe(
                (res: IInventorySubLocation[]) => (this.inventorysublocations = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.vendorService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IVendor[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVendor[]>) => response.body)
            )
            .subscribe((res: IVendor[]) => (this.vendors = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.stockInProcess.processStartedOn = this.processStartedOn != null ? moment(this.processStartedOn, DATE_TIME_FORMAT) : null;
        this.stockInProcess.stockInDate = this.stockInDate != null ? moment(this.stockInDate, DATE_TIME_FORMAT) : null;
        if (this.stockInProcess.id !== undefined) {
            this.subscribeToSaveResponse(this.stockInProcessService.update(this.stockInProcess));
        } else {
            this.subscribeToSaveResponse(this.stockInProcessService.create(this.stockInProcess));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockInProcess>>) {
        result.subscribe((res: HttpResponse<IStockInProcess>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPurchaseOrderById(index: number, item: IPurchaseOrder) {
        return item.id;
    }

    trackCommercialPurchaseOrderById(index: number, item: ICommercialPurchaseOrder) {
        return item.id;
    }

    trackProductCategoryById(index: number, item: IProductCategory) {
        return item.id;
    }

    trackProductById(index: number, item: IProduct) {
        return item.id;
    }

    trackInventoryLocationById(index: number, item: IInventoryLocation) {
        return item.id;
    }

    trackInventorySubLocationById(index: number, item: IInventorySubLocation) {
        return item.id;
    }

    trackVendorById(index: number, item: IVendor) {
        return item.id;
    }
}

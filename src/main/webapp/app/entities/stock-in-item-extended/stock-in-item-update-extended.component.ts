import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IStockInItem } from 'app/shared/model/stock-in-item.model';
import { StockInItemExtendedService } from './stock-in-item-extended.service';
import { IItemCategory } from 'app/shared/model/item-category.model';
import { ItemCategoryService } from 'app/entities/item-category';
import { IItemSubCategory } from 'app/shared/model/item-sub-category.model';
import { ItemSubCategoryService } from 'app/entities/item-sub-category';
import { IInventoryLocation } from 'app/shared/model/inventory-location.model';
import { InventoryLocationService } from 'app/entities/inventory-location';
import { IInventorySubLocation } from 'app/shared/model/inventory-sub-location.model';
import { InventorySubLocationService } from 'app/entities/inventory-sub-location';
import { IManufacturer } from 'app/shared/model/manufacturer.model';
import { ManufacturerService } from 'app/entities/manufacturer';
import { IStockInProcess } from 'app/shared/model/stock-in-process.model';
import { StockInProcessService } from 'app/entities/stock-in-process';
import { StockInItemUpdateComponent } from 'app/entities/stock-in-item';
import { VendorService } from 'app/entities/vendor';
import { ProductCategoryService } from 'app/entities/product-category';
import { ProductService } from 'app/entities/product';
import { PurchaseOrderService } from 'app/entities/purchase-order';
import { CommercialPurchaseOrderService } from 'app/entities/commercial-purchase-order';

@Component({
    selector: 'jhi-stock-in-item-update-extended',
    templateUrl: './stock-in-item-update-extended.component.html'
})
export class StockInItemUpdateExtendedComponent extends StockInItemUpdateComponent implements OnInit {
    stockInItem: IStockInItem;
    isSaving: boolean;

    itemcategories: IItemCategory[];

    itemsubcategories: IItemSubCategory[];

    inventorylocations: IInventoryLocation[];

    inventorysublocations: IInventorySubLocation[];

    manufacturers: IManufacturer[];

    stockinprocesses: IStockInProcess[];
    expiryDateDp: any;
    stockInDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected stockInItemService: StockInItemExtendedService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected inventoryLocationService: InventoryLocationService,
        protected inventorySubLocationService: InventorySubLocationService,
        protected vendorService: VendorService,
        protected stockInProcessService: StockInProcessService,
        protected purchaseOrderService: PurchaseOrderService,
        protected commercialPurchaseOrderService: CommercialPurchaseOrderService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(
            jhiAlertService,
            stockInItemService,
            productCategoryService,
            productService,
            inventoryLocationService,
            inventorySubLocationService,
            vendorService,
            stockInProcessService,
            purchaseOrderService,
            commercialPurchaseOrderService,
            activatedRoute
        );
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stockInItem }) => {
            this.stockInItem = stockInItem;
            this.stockInDate = this.stockInItem.stockInDate != null ? this.stockInItem.stockInDate.format(DATE_TIME_FORMAT) : null;
        });
        this.productCategoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IItemCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IItemCategory[]>) => response.body)
            )
            .subscribe((res: IItemCategory[]) => (this.itemcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IItemSubCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IItemSubCategory[]>) => response.body)
            )
            .subscribe((res: IItemSubCategory[]) => (this.itemsubcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
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
                filter((mayBeOk: HttpResponse<IManufacturer[]>) => mayBeOk.ok),
                map((response: HttpResponse<IManufacturer[]>) => response.body)
            )
            .subscribe((res: IManufacturer[]) => (this.manufacturers = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.stockInProcessService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStockInProcess[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStockInProcess[]>) => response.body)
            )
            .subscribe((res: IStockInProcess[]) => (this.stockinprocesses = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.stockInItem.stockInDate = this.stockInDate != null ? moment(this.stockInDate, DATE_TIME_FORMAT) : null;
        if (this.stockInItem.id !== undefined) {
            this.subscribeToSaveResponse(this.stockInItemService.update(this.stockInItem));
        } else {
            this.subscribeToSaveResponse(this.stockInItemService.create(this.stockInItem));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockInItem>>) {
        result.subscribe((res: HttpResponse<IStockInItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackItemCategoryById(index: number, item: IItemCategory) {
        return item.id;
    }

    trackItemSubCategoryById(index: number, item: IItemSubCategory) {
        return item.id;
    }

    trackInventoryLocationById(index: number, item: IInventoryLocation) {
        return item.id;
    }

    trackInventorySubLocationById(index: number, item: IInventorySubLocation) {
        return item.id;
    }

    trackManufacturerById(index: number, item: IManufacturer) {
        return item.id;
    }

    trackStockInProcessById(index: number, item: IStockInProcess) {
        return item.id;
    }
}

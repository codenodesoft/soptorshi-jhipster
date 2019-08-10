import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IStockOutItem } from 'app/shared/model/stock-out-item.model';
import { StockOutItemService } from './stock-out-item.service';
import { IItemCategory } from 'app/shared/model/item-category.model';
import { ItemCategoryService } from 'app/entities/item-category';
import { IItemSubCategory } from 'app/shared/model/item-sub-category.model';
import { ItemSubCategoryService } from 'app/entities/item-sub-category';
import { IInventoryLocation } from 'app/shared/model/inventory-location.model';
import { InventoryLocationService } from 'app/entities/inventory-location';
import { IInventorySubLocation } from 'app/shared/model/inventory-sub-location.model';
import { InventorySubLocationService } from 'app/entities/inventory-sub-location';
import { IStockInItem } from 'app/shared/model/stock-in-item.model';
import { StockInItemService } from 'app/entities/stock-in-item';
import { IStockStatus } from 'app/shared/model/stock-status.model';
import { StockStatusService } from 'app/entities/stock-status';

@Component({
    selector: 'jhi-stock-out-item-update',
    templateUrl: './stock-out-item-update.component.html'
})
export class StockOutItemUpdateComponent implements OnInit {
    stockOutItem: IStockOutItem;
    isSaving: boolean;

    itemcategories: IItemCategory[];

    itemsubcategories: IItemSubCategory[];

    inventorylocations: IInventoryLocation[];

    inventorysublocations: IInventorySubLocation[];

    stockinitems: IStockInItem[];

    stockstatuses: IStockStatus[];
    stockOutDate: string;

    stockStatusesForContainers: IStockStatus[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected stockOutItemService: StockOutItemService,
        protected itemCategoryService: ItemCategoryService,
        protected itemSubCategoryService: ItemSubCategoryService,
        protected inventoryLocationService: InventoryLocationService,
        protected inventorySubLocationService: InventorySubLocationService,
        protected stockInItemService: StockInItemService,
        protected stockStatusService: StockStatusService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stockOutItem }) => {
            this.stockOutItem = stockOutItem;
            this.stockOutDate = this.stockOutItem.stockOutDate != null ? this.stockOutItem.stockOutDate.format(DATE_TIME_FORMAT) : null;
        });
        this.itemCategoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IItemCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IItemCategory[]>) => response.body)
            )
            .subscribe((res: IItemCategory[]) => (this.itemcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.itemSubCategoryService
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
        this.stockInItemService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStockInItem[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStockInItem[]>) => response.body)
            )
            .subscribe((res: IStockInItem[]) => (this.stockinitems = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.stockStatusService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStockStatus[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStockStatus[]>) => response.body)
            )
            .subscribe((res: IStockStatus[]) => (this.stockstatuses = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    getItemSubCategories() {
        this.itemSubCategoryService
            .query({
                'itemCategoriesId.equals': this.stockOutItem.itemCategoriesId
            })
            .pipe(
                filter((mayBeOk: HttpResponse<IItemSubCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IItemSubCategory[]>) => response.body)
            )
            .subscribe((res: IItemSubCategory[]) => (this.itemsubcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    getInventorySubLocation() {
        this.inventorySubLocationService
            .query({
                'inventoryLocationsId.equals': this.stockOutItem.inventoryLocationsId
            })
            .pipe(
                filter((mayBeOk: HttpResponse<IInventorySubLocation[]>) => mayBeOk.ok),
                map((response: HttpResponse<IInventorySubLocation[]>) => response.body)
            )
            .subscribe(
                (res: IInventorySubLocation[]) => (this.inventorysublocations = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    getContainerTrackingId() {
        if (
            this.stockOutItem.itemCategoriesId &&
            this.stockOutItem.itemSubCategoriesId &&
            this.stockOutItem.inventoryLocationsId &&
            this.stockOutItem.inventorySubLocationsId
        ) {
            this.stockStatusService
                .query({
                    'itemCategoriesId.equals': this.stockOutItem.itemCategoriesId,
                    'itemSubCategoriesId.equals': this.stockOutItem.itemSubCategoriesId,
                    'inventoryLocationsId.equals': this.stockOutItem.inventoryLocationsId,
                    'inventorySubLocationsId.equals': this.stockOutItem.inventorySubLocationsId
                })
                .subscribe(
                    (res: HttpResponse<IStockStatus[]>) => this.extractStockStatusForContainers(res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    getRemainingQuantity() {
        if (
            this.stockOutItem.itemCategoriesId &&
            this.stockOutItem.itemSubCategoriesId &&
            this.stockOutItem.inventoryLocationsId &&
            this.stockOutItem.inventorySubLocationsId &&
            this.stockOutItem.containerTrackingId
        ) {
            this.stockStatusService
                .query({
                    'itemCategoriesId.equals': this.stockOutItem.itemCategoriesId,
                    'itemSubCategoriesId.equals': this.stockOutItem.itemSubCategoriesId,
                    'inventoryLocationsId.equals': this.stockOutItem.inventoryLocationsId,
                    'inventorySubLocationsId.equals': this.stockOutItem.inventorySubLocationsId,
                    'containerTrackingId.equals': this.stockOutItem.containerTrackingId
                })
                .subscribe(
                    (res: HttpResponse<IStockStatus[]>) => (this.stockOutItem.quantity = res.body[0].availableQuantity),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    extractStockStatusForContainers(data: IStockStatus[]) {
        this.stockStatusesForContainers = [];
        for (let i = 0; i < data.length; i++) {
            this.stockStatusesForContainers.push(data[i]);
        }
    }

    save() {
        this.isSaving = true;
        this.stockOutItem.stockOutDate = this.stockOutDate != null ? moment(this.stockOutDate, DATE_TIME_FORMAT) : null;
        if (this.stockOutItem.id !== undefined) {
            this.subscribeToSaveResponse(this.stockOutItemService.update(this.stockOutItem));
        } else {
            this.subscribeToSaveResponse(this.stockOutItemService.create(this.stockOutItem));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockOutItem>>) {
        result.subscribe((res: HttpResponse<IStockOutItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackStockInItemById(index: number, item: IStockInItem) {
        return item.id;
    }

    trackStockStatusById(index: number, item: IStockStatus) {
        return item.id;
    }

    trackStockStatusForContainersById(index: number, item: IStockStatus) {
        return item.id;
    }
}

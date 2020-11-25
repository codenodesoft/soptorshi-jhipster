import { Component, OnInit, ViewChild } from '@angular/core';
import { ProductService, ProductUpdateComponent } from 'app/entities/product';
import { IMstAccount, MstAccount } from 'app/shared/model/mst-account.model';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ProductCategoryService } from 'app/entities/product-category';
import { ActivatedRoute } from '@angular/router';
import { MstAccountExtendedService } from 'app/entities/mst-account-extended';
import { merge, Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, filter, map } from 'rxjs/operators';
import { NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IProductCategory } from 'app/shared/model/product-category.model';

@Component({
    selector: 'jhi-product-update',
    templateUrl: './product-extended-update.component.html'
})
export class ProductExtendedUpdateComponent extends ProductUpdateComponent implements OnInit {
    accounts: IMstAccount[];
    selectedAccount: IMstAccount = new MstAccount();
    accountNames: string[];
    accountNameMapAccount: any;
    selectAccountName: string;

    @ViewChild('instance') instance: NgbTypeahead;
    focus$ = new Subject<string>();
    click$ = new Subject<string>();

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected productService: ProductService,
        protected productCategoryService: ProductCategoryService,
        protected activatedRoute: ActivatedRoute,
        private mstAccountService: MstAccountExtendedService
    ) {
        super(dataUtils, jhiAlertService, productService, productCategoryService, activatedRoute);
    }

    ngOnInit() {
        this.mstAccountService
            .query({
                size: 10000
            })
            .subscribe(res => {
                this.accounts = res.body;
                this.accountNameMapAccount = {};
                this.accountNames = [];
                this.accounts.forEach(a => {
                    this.accountNameMapAccount[a.name] = a;
                    this.accountNames.push(a.name);
                });
            });
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ product }) => {
            this.product = product;
            this.selectAccountName = this.product.accountName;
        });
        this.productCategoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductCategory[]>) => response.body)
            )
            .subscribe((res: IProductCategory[]) => (this.productcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    save() {
        this.isSaving = true;
        if (this.selectAccountName) this.product.accountId = this.accountNameMapAccount[this.selectAccountName].id;
        if (this.product.id !== undefined) {
            this.subscribeToSaveResponse(this.productService.update(this.product));
        } else {
            this.subscribeToSaveResponse(this.productService.create(this.product));
        }
    }

    search = (text$: Observable<string>) => {
        const debouncedText$ = text$.pipe(
            debounceTime(200),
            distinctUntilChanged()
        );
        const clicksWithClosedPopup$ = this.click$.pipe(filter(() => !this.instance.isPopupOpen()));
        const inputFocus$ = this.focus$;

        return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
            map(term =>
                (term === '' ? this.accountNames : this.accountNames.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1)).slice(
                    0,
                    10
                )
            )
        );
    };
}

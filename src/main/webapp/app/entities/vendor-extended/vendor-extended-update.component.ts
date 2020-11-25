import { Component, OnInit, ViewChild } from '@angular/core';
import { VendorService, VendorUpdateComponent } from 'app/entities/vendor';
import { JhiDataUtils } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { MstAccountExtendedService } from 'app/entities/mst-account-extended';
import { NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
import { merge, Observable, Subject } from 'rxjs';
import { IMstAccount } from 'app/shared/model/mst-account.model';
import { debounceTime, distinctUntilChanged, filter, map } from 'rxjs/operators';

@Component({
    selector: 'jhi-vendor-extended-update',
    templateUrl: './vendor-extended-update.component.html'
})
export class VendorExtendedUpdateComponent extends VendorUpdateComponent implements OnInit {
    accounts: IMstAccount[];
    accountNames: string[];
    accountNameMapAccount: any;
    selectAccountName: string;

    @ViewChild('instance') instance: NgbTypeahead;
    focus$ = new Subject<string>();
    click$ = new Subject<string>();

    constructor(
        protected dataUtils: JhiDataUtils,
        protected vendorService: VendorService,
        protected activatedRoute: ActivatedRoute,
        private mstAccountService: MstAccountExtendedService
    ) {
        super(dataUtils, vendorService, activatedRoute);
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
        this.activatedRoute.data.subscribe(({ vendor }) => {
            this.vendor = vendor;
            this.selectAccountName = this.vendor.accountName;
        });
    }

    save() {
        if (this.selectAccountName) this.vendor.accountId = this.accountNameMapAccount[this.selectAccountName].id;
        super.save();
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

import { Component, OnDestroy, OnInit } from '@angular/core';
import { AccountBalanceExtendedService } from 'app/entities/account-balance-extended/account-balance-extended.service';
import { Moment } from 'Moment';

export const enum BalanceSheetFetchType {
    SUMMARIZED,
    DETAILED
}

@Component({
    selector: 'jhi-balance-sheet',
    templateUrl: './balance-sheet.component.html'
})
export class BalanceSheetComponent implements OnInit, OnDestroy {
    asOnDate: any;
    fetchType: BalanceSheetFetchType;

    constructor(private accountBalanceExtendedService: AccountBalanceExtendedService) {}

    ngOnDestroy(): void {}

    ngOnInit(): void {
        this.asOnDate = new Date();
        this.fetchType = BalanceSheetFetchType.SUMMARIZED;
    }

    downloadBalanceSheet() {
        this.accountBalanceExtendedService.downloadBalanceSheet(this.fetchType, this.asOnDate);
    }
}

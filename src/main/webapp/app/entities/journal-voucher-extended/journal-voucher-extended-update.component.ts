import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Moment } from 'moment';
import { JournalVoucherExtendedService } from './journal-voucher-extended.service';
import { JournalVoucherService, JournalVoucherUpdateComponent } from 'app/entities/journal-voucher';
import { CurrencyFlag, ICurrency } from 'app/shared/model/currency.model';
import { VoucherType } from 'app/shared/model/dt-transaction.model';
import { IConversionFactor } from 'app/shared/model/conversion-factor.model';
import { CurrencyExtendedService } from 'app/entities/currency-extended';
import { ConversionFactorExtendedService } from 'app/entities/conversion-factor-extended';
import { JhiAlertService } from 'ng-jhipster';
import { CurrencyService } from 'app/entities/currency';

@Component({
    selector: 'jhi-journal-voucher-update',
    templateUrl: './journal-voucher-extended-update.component.html'
})
export class JournalVoucherExtendedUpdateComponent extends JournalVoucherUpdateComponent implements OnInit {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected journalVoucherService: JournalVoucherExtendedService,
        protected currencyService: CurrencyService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, journalVoucherService, currencyService, activatedRoute);
    }
}

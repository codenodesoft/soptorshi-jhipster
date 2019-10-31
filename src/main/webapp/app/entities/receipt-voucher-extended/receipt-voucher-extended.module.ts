import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ReceiptVoucherComponent,
    ReceiptVoucherDeleteDialogComponent,
    ReceiptVoucherDeletePopupComponent,
    ReceiptVoucherDetailComponent,
    receiptVoucherPopupRoute,
    ReceiptVoucherUpdateComponent
} from 'app/entities/receipt-voucher';
import {
    receiptVoucherExtendedPopupRoute,
    receiptVoucherExtendedRoute
} from 'app/entities/receipt-voucher-extended/receipt-voucher-extended.route';
import { ReceiptVoucherExtendedComponent } from 'app/entities/receipt-voucher-extended/receipt-voucher-extended.component';
import { ReceiptVoucherExtendedDetailComponent } from 'app/entities/receipt-voucher-extended/receipt-voucher-extended-detail.component';
import { ReceiptVoucherExtendedUpdateComponent } from 'app/entities/receipt-voucher-extended/receipt-voucher-extended-update.component';
import { ReceiptVoucherTransactionComponent } from 'app/entities/receipt-voucher-extended/receipt-voucher-transaction.component';
import { ReceiptVoucherTransactionUpdateComponent } from 'app/entities/receipt-voucher-extended/receipt-voucher-transaction-update.component';

const ENTITY_STATES = [...receiptVoucherExtendedRoute, ...receiptVoucherExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ReceiptVoucherComponent,
        ReceiptVoucherDetailComponent,
        ReceiptVoucherUpdateComponent,
        ReceiptVoucherDeleteDialogComponent,
        ReceiptVoucherDeletePopupComponent,
        ReceiptVoucherExtendedComponent,
        ReceiptVoucherExtendedDetailComponent,
        ReceiptVoucherExtendedUpdateComponent,
        ReceiptVoucherTransactionComponent,
        ReceiptVoucherTransactionUpdateComponent
    ],
    entryComponents: [
        ReceiptVoucherComponent,
        ReceiptVoucherUpdateComponent,
        ReceiptVoucherDeleteDialogComponent,
        ReceiptVoucherDeletePopupComponent,
        ReceiptVoucherTransactionUpdateComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiReceiptVoucherModule {}
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    AllowanceManagementComponent,
    AllowanceManagementDetailComponent,
    AllowanceManagementUpdateComponent,
    AllowanceManagementDeletePopupComponent,
    AllowanceManagementDeleteDialogComponent,
    allowanceManagementRoute,
    allowanceManagementPopupRoute
} from './';

const ENTITY_STATES = [...allowanceManagementRoute, ...allowanceManagementPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AllowanceManagementComponent,
        AllowanceManagementDetailComponent,
        AllowanceManagementUpdateComponent,
        AllowanceManagementDeleteDialogComponent,
        AllowanceManagementDeletePopupComponent
    ],
    entryComponents: [
        AllowanceManagementComponent,
        AllowanceManagementUpdateComponent,
        AllowanceManagementDeleteDialogComponent,
        AllowanceManagementDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiAllowanceManagementModule {}

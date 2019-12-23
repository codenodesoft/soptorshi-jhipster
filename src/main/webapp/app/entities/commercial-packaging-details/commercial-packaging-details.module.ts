import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialPackagingDetailsComponent,
    CommercialPackagingDetailsDetailComponent,
    CommercialPackagingDetailsUpdateComponent,
    CommercialPackagingDetailsDeletePopupComponent,
    CommercialPackagingDetailsDeleteDialogComponent,
    commercialPackagingDetailsRoute,
    commercialPackagingDetailsPopupRoute
} from './';

const ENTITY_STATES = [...commercialPackagingDetailsRoute, ...commercialPackagingDetailsPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialPackagingDetailsComponent,
        CommercialPackagingDetailsDetailComponent,
        CommercialPackagingDetailsUpdateComponent,
        CommercialPackagingDetailsDeleteDialogComponent,
        CommercialPackagingDetailsDeletePopupComponent
    ],
    entryComponents: [
        CommercialPackagingDetailsComponent,
        CommercialPackagingDetailsUpdateComponent,
        CommercialPackagingDetailsDeleteDialogComponent,
        CommercialPackagingDetailsDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialPackagingDetailsModule {}

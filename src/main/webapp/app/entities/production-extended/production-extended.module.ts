import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ProductionDeleteDialogExtendedComponent,
    ProductionDeletePopupExtendedComponent,
    ProductionDetailExtendedComponent,
    ProductionExtendedComponent,
    productionExtendedRoute,
    productionPopupExtendedRoute,
    ProductionUpdateExtendedComponent
} from './';
import {
    ProductComponent,
    ProductDeleteDialogComponent,
    ProductDeletePopupComponent,
    ProductDetailComponent,
    ProductUpdateComponent
} from 'app/entities/product';

const ENTITY_STATES = [...productionExtendedRoute, ...productionPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductComponent,
        ProductDetailComponent,
        ProductUpdateComponent,
        ProductDeleteDialogComponent,
        ProductDeletePopupComponent,
        ProductionExtendedComponent,
        ProductionDetailExtendedComponent,
        ProductionUpdateExtendedComponent,
        ProductionDeleteDialogExtendedComponent,
        ProductionDeletePopupExtendedComponent
    ],
    entryComponents: [
        ProductionExtendedComponent,
        ProductionUpdateExtendedComponent,
        ProductionDeleteDialogExtendedComponent,
        ProductionDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiProductionExtendedModule {}

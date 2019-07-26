import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';

import { ProductDeleteDialogComponent, ProductDeletePopupComponent } from 'app/entities/product';
import { ProductExtendedUpdateComponent } from 'app/entities/product-extended/product-extended-update.component';
import { ProductExtendedComponent } from 'app/entities/product-extended/product-extended.component';
import { ProductExtendedDetailComponent } from 'app/entities/product-extended/product-extended-detail.component';
import { productExtendedPopupRoute, productExtendedRoute } from 'app/entities/product-extended/product-extended.route';

const ENTITY_STATES = [...productExtendedRoute, ...productExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductExtendedComponent,
        ProductExtendedDetailComponent,
        ProductExtendedUpdateComponent,
        ProductDeleteDialogComponent,
        ProductDeletePopupComponent
    ],
    entryComponents: [ProductExtendedComponent, ProductExtendedUpdateComponent, ProductDeleteDialogComponent, ProductDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiProductExtendedModule {}

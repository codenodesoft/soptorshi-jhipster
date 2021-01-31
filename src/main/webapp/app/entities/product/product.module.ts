import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import { ProductComponent, productPopupRoute, productRoute, ProductUpdateComponent } from './';

const ENTITY_STATES = [...productRoute, ...productPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ProductComponent, ProductUpdateComponent],
    entryComponents: [ProductComponent, ProductUpdateComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiProductModule {}

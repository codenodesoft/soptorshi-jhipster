import { Moment } from 'moment';

export const enum ProductionWeightStep {
    RAW = 'RAW',
    FILLET = 'FILLET',
    PROCESS_FILLET = 'PROCESS_FILLET',
    FREEZING = 'FREEZING'
}

export const enum UnitOfMeasurements {
    PCS = 'PCS',
    KG = 'KG',
    TON = 'TON',
    GRAM = 'GRAM'
}

export interface IProduction {
    id?: number;
    weightStep?: ProductionWeightStep;
    unit?: UnitOfMeasurements;
    quantity?: number;
    byProductDescription?: string;
    byProductQuantity?: number;
    remarks?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    wastage?: number;
    productCategoriesName?: string;
    productCategoriesId?: number;
    productsName?: string;
    productsId?: number;
    requisitionsRequisitionNo?: string;
    requisitionsId?: number;
}

export class Production implements IProduction {
    constructor(
        public id?: number,
        public weightStep?: ProductionWeightStep,
        public unit?: UnitOfMeasurements,
        public quantity?: number,
        public byProductDescription?: string,
        public byProductQuantity?: number,
        public remarks?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public wastage?: number,
        public productCategoriesName?: string,
        public productCategoriesId?: number,
        public productsName?: string,
        public productsId?: number,
        public requisitionsRequisitionNo?: string,
        public requisitionsId?: number
    ) {}
}

import { Moment } from 'moment';

export const enum ProductStatus {
    GOOD = 'GOOD',
    NOT_GOOD = 'NOT_GOOD',
    NOT_JUSTIFIED = 'NOT_JUSTIFIED'
}

export interface IProduct {
    id?: number;
    name?: string;
    description?: any;
    countryOrOrigin?: string;
    status?: ProductStatus;
    modifiedBy?: string;
    modifiedOn?: Moment;
    scientificName?: string;
    productCategoryName?: string;
    productCategoryId?: number;
    productId?: number;
}

export class Product implements IProduct {
    constructor(
        public id?: number,
        public name?: string,
        public description?: any,
        public countryOrOrigin?: string,
        public status?: ProductStatus,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public scientificName?: string,
        public productCategoryName?: string,
        public productCategoryId?: number,
        public productId?: number
    ) {}
}

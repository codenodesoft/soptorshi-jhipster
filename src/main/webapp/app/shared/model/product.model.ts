import { Moment } from 'moment';

export const enum ProductStatus {
    GOOD = 'GOOD',
    NOT_GOOD = 'NOT_GOOD',
    NOT_JUSTIFIED = 'NOT_JUSTIFIED'
}

export interface IProduct {
    id?: number;
    productId?: number;
    name?: string;
    description?: any;
    countryOrOrigin?: string;
    status?: ProductStatus;
    modifiedBy?: string;
    modifiedOn?: Moment;
    productCategoryName?: string;
    productCategoryId?: number;
    accountId?: number;
    accountName?: string;
}

export class Product implements IProduct {
    constructor(
        public id?: number,
        public productId?: number,
        public name?: string,
        public description?: any,
        public countryOrOrigin?: string,
        public status?: ProductStatus,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public productCategoryName?: string,
        public productCategoryId?: number,
        public accountId?: number,
        public accountName?: string
    ) {}
}

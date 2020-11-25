export const enum VendorRemarks {
    VERY_GOOD = 'VERY_GOOD',
    GOOD = 'GOOD',
    MODERATE = 'MODERATE',
    AVERAGE = 'AVERAGE',
    BELOW_AVERAGE = 'BELOW_AVERAGE',
    BAD = 'BAD'
}

export interface IVendor {
    id?: number;
    companyName?: string;
    description?: any;
    address?: any;
    contactNumber?: string;
    remarks?: VendorRemarks;
    accountId?: number;
    accountName?: string;
}

export class Vendor implements IVendor {
    constructor(
        public id?: number,
        public companyName?: string,
        public description?: any,
        public address?: any,
        public contactNumber?: string,
        public remarks?: VendorRemarks,
        public accountId?: number,
        public accountName?: string
    ) {}
}

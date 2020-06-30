import { Moment } from 'moment';
import { SupplyOrderDetails } from 'app/shared/model/supply-order-details.model';

export const enum SupplyOrderStatus {
    ORDER_RECEIVED = 'ORDER_RECEIVED',
    PROCESSING_ORDER = 'PROCESSING_ORDER',
    ORDER_DELIVERED_AND_WAITING_FOR_MONEY_COLLECTION = 'ORDER_DELIVERED_AND_WAITING_FOR_MONEY_COLLECTION',
    ORDER_CLOSE = 'ORDER_CLOSE'
}

export interface ISupplyOrder {
    id?: number;
    orderNo?: string;
    dateOfOrder?: Moment;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    deliveryDate?: Moment;
    status?: SupplyOrderStatus;
    areaWiseAccumulationRefNo?: string;
    remarks?: string;
    supplyZoneName?: string;
    supplyZoneId?: number;
    supplyZoneManagerId?: number;
    supplyZoneManagerName?: string;
    supplyAreaName?: string;
    supplyAreaId?: number;
    supplySalesRepresentativeName?: string;
    supplySalesRepresentativeId?: number;
    supplyAreaManagerId?: number;
    supplyAreaManagerName?: string;
    supplyOrderDetails?: SupplyOrderDetails[];
}

export class SupplyOrder implements ISupplyOrder {
    constructor(
        public id?: number,
        public orderNo?: string,
        public dateOfOrder?: Moment,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public deliveryDate?: Moment,
        public status?: SupplyOrderStatus,
        public areaWiseAccumulationRefNo?: string,
        public remarks?: string,
        public supplyZoneName?: string,
        public supplyZoneId?: number,
        public supplyZoneManagerId?: number,
        public supplyZoneManagerName?: string,
        public supplyAreaName?: string,
        public supplyAreaId?: number,
        public supplySalesRepresentativeName?: string,
        public supplySalesRepresentativeId?: number,
        public supplyAreaManagerId?: number,
        public supplyAreaManagerName?: string,
        public supplyOrderDetails?: SupplyOrderDetails[]
    ) {}
}

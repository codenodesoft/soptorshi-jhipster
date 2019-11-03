import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommercialWorkOrderDetails } from 'app/shared/model/commercial-work-order-details.model';

type EntityResponseType = HttpResponse<ICommercialWorkOrderDetails>;
type EntityArrayResponseType = HttpResponse<ICommercialWorkOrderDetails[]>;

@Injectable({ providedIn: 'root' })
export class CommercialWorkOrderDetailsService {
    public resourceUrl = SERVER_API_URL + 'api/commercial-work-order-details';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/commercial-work-order-details';

    constructor(protected http: HttpClient) {}

    create(commercialWorkOrderDetails: ICommercialWorkOrderDetails): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialWorkOrderDetails);
        return this.http
            .post<ICommercialWorkOrderDetails>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(commercialWorkOrderDetails: ICommercialWorkOrderDetails): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialWorkOrderDetails);
        return this.http
            .put<ICommercialWorkOrderDetails>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICommercialWorkOrderDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialWorkOrderDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialWorkOrderDetails[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(commercialWorkOrderDetails: ICommercialWorkOrderDetails): ICommercialWorkOrderDetails {
        const copy: ICommercialWorkOrderDetails = Object.assign({}, commercialWorkOrderDetails, {
            createOn:
                commercialWorkOrderDetails.createOn != null && commercialWorkOrderDetails.createOn.isValid()
                    ? commercialWorkOrderDetails.createOn.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createOn = res.body.createOn != null ? moment(res.body.createOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((commercialWorkOrderDetails: ICommercialWorkOrderDetails) => {
                commercialWorkOrderDetails.createOn =
                    commercialWorkOrderDetails.createOn != null ? moment(commercialWorkOrderDetails.createOn) : null;
            });
        }
        return res;
    }
}

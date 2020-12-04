import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ICommercialPo } from 'app/shared/model/commercial-po.model';
import { CommercialPoService } from 'app/entities/commercial-po';
import { SoptorshiUtil } from 'app/shared/util/SoptorshiUtil';

type EntityResponseType = HttpResponse<ICommercialPo>;
type EntityArrayResponseType = HttpResponse<ICommercialPo[]>;

@Injectable({ providedIn: 'root' })
export class CommercialPoExtendedService extends CommercialPoService {
    public resourceUrl = SERVER_API_URL + 'api/extended/commercial-pos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/commercial-pos';

    constructor(protected http: HttpClient) {
        super(http);
    }

    generatePo(poNumber: number) {
        return this.http
            .get(`${this.resourceUrl}/report/commercialPo/${poNumber}`, {
                responseType: 'blob'
            })
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', 'Commercial Po');
            });
    }
}

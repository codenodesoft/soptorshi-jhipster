/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CommercialInvoiceService } from 'app/entities/commercial-invoice/commercial-invoice.service';
import { ICommercialInvoice, CommercialInvoice } from 'app/shared/model/commercial-invoice.model';

describe('Service Tests', () => {
    describe('CommercialInvoice Service', () => {
        let injector: TestBed;
        let service: CommercialInvoiceService;
        let httpMock: HttpTestingController;
        let elemDefault: ICommercialInvoice;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CommercialInvoiceService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new CommercialInvoice(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                currentDate
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdOn: currentDate.format(DATE_FORMAT),
                        updatedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a CommercialInvoice', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        createdOn: currentDate.format(DATE_FORMAT),
                        updatedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdOn: currentDate,
                        updatedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new CommercialInvoice(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CommercialInvoice', async () => {
                const returnedFromService = Object.assign(
                    {
                        invoiceNo: 'BBBBBB',
                        invoiceDate: 'BBBBBB',
                        termsOfPayment: 'BBBBBB',
                        consignedTo: 'BBBBBB',
                        portOfLoading: 'BBBBBB',
                        portOfDischarge: 'BBBBBB',
                        exportRegistrationCertificateNo: 'BBBBBB',
                        createdBy: 'BBBBBB',
                        createdOn: currentDate.format(DATE_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        createdOn: currentDate,
                        updatedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of CommercialInvoice', async () => {
                const returnedFromService = Object.assign(
                    {
                        invoiceNo: 'BBBBBB',
                        invoiceDate: 'BBBBBB',
                        termsOfPayment: 'BBBBBB',
                        consignedTo: 'BBBBBB',
                        portOfLoading: 'BBBBBB',
                        portOfDischarge: 'BBBBBB',
                        exportRegistrationCertificateNo: 'BBBBBB',
                        createdBy: 'BBBBBB',
                        createdOn: currentDate.format(DATE_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdOn: currentDate,
                        updatedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a CommercialInvoice', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});

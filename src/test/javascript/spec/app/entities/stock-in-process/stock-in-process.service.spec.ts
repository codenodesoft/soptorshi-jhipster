/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { StockInProcessService } from 'app/entities/stock-in-process/stock-in-process.service';
import { IStockInProcess, StockInProcess, ItemUnit, ContainerCategory } from 'app/shared/model/stock-in-process.model';

describe('Service Tests', () => {
    describe('StockInProcess Service', () => {
        let injector: TestBed;
        let service: StockInProcessService;
        let httpMock: HttpTestingController;
        let elemDefault: IStockInProcess;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(StockInProcessService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new StockInProcess(
                0,
                0,
                ItemUnit.KG,
                0,
                0,
                ContainerCategory.BOTTLE,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        expiryDate: currentDate.format(DATE_FORMAT),
                        stockInDate: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a StockInProcess', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        expiryDate: currentDate.format(DATE_FORMAT),
                        stockInDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        expiryDate: currentDate,
                        stockInDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new StockInProcess(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a StockInProcess', async () => {
                const returnedFromService = Object.assign(
                    {
                        totalQuantity: 1,
                        unit: 'BBBBBB',
                        unitPrice: 1,
                        totalContainer: 1,
                        containerCategory: 'BBBBBB',
                        containerTrackingId: 'BBBBBB',
                        quantityPerContainer: 'BBBBBB',
                        expiryDate: currentDate.format(DATE_FORMAT),
                        stockInBy: 'BBBBBB',
                        stockInDate: currentDate.format(DATE_TIME_FORMAT),
                        purchaseOrderId: 'BBBBBB',
                        remarks: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        expiryDate: currentDate,
                        stockInDate: currentDate
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

            it('should return a list of StockInProcess', async () => {
                const returnedFromService = Object.assign(
                    {
                        totalQuantity: 1,
                        unit: 'BBBBBB',
                        unitPrice: 1,
                        totalContainer: 1,
                        containerCategory: 'BBBBBB',
                        containerTrackingId: 'BBBBBB',
                        quantityPerContainer: 'BBBBBB',
                        expiryDate: currentDate.format(DATE_FORMAT),
                        stockInBy: 'BBBBBB',
                        stockInDate: currentDate.format(DATE_TIME_FORMAT),
                        purchaseOrderId: 'BBBBBB',
                        remarks: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        expiryDate: currentDate,
                        stockInDate: currentDate
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

            it('should delete a StockInProcess', async () => {
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

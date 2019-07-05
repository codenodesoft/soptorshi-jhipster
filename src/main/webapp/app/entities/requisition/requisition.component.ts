import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IRequisition } from 'app/shared/model/requisition.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { RequisitionService } from './requisition.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';

@Component({
    selector: 'jhi-requisition',
    templateUrl: './requisition.component.html'
})
export class RequisitionComponent implements OnInit, OnDestroy {
    currentEmployee: IEmployee;
    currentAccount: any;
    requisitions: IRequisition[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    routeData: any;
    links: any;
    totalItems: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(
        protected requisitionService: RequisitionService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        public accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected dataUtils: JhiDataUtils,
        protected router: Router,
        protected eventManager: JhiEventManager,
        protected employeeService: EmployeeService
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.requisitionService
                .search({
                    page: this.page - 1,
                    query: this.currentSearch,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IRequisition[]>) => this.paginateRequisitions(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        if (this.accountService.hasAnyAuthority(['ROLE_CFO', 'ROLE_PURCHASE_COMMITTEE'])) {
            this.requisitionService
                .query({
                    page: this.page - 1,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IRequisition[]>) => this.paginateRequisitions(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else if (this.accountService.hasAnyAuthority(['ROLE_REQUISITION'])) {
            this.requisitionService
                .query({
                    'employeeId.equals': this.currentEmployee.id,
                    page: this.page - 1,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IRequisition[]>) => this.paginateRequisitions(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else if (this.accountService.hasAnyAuthority(['ROLE_DEPARTMENT_HEAD'])) {
            this.requisitionService
                .query({
                    'departmentId.equals': this.currentEmployee.departmentId,
                    'officeId.equals': this.currentEmployee.officeId,
                    page: this.page - 1,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IRequisition[]>) => this.paginateRequisitions(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/requisition'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                search: this.currentSearch,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.currentSearch = '';
        this.router.navigate([
            '/requisition',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.page = 0;
        this.currentSearch = query;
        this.router.navigate([
            '/requisition',
            {
                search: this.currentSearch,
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.employeeService
                .query({
                    'employeeId.equals': this.currentAccount.login
                })
                .subscribe(
                    (res: HttpResponse<IEmployee[]>) => {
                        this.currentEmployee = res.body[0];
                        this.loadAll();
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        });
        this.registerChangeInRequisitions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRequisition) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInRequisitions() {
        this.eventSubscriber = this.eventManager.subscribe('requisitionListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateRequisitions(data: IRequisition[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.requisitions = data;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
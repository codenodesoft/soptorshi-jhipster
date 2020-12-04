import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEmployee } from 'app/shared/model/employee.model';
import { IDepartment } from 'app/shared/model/department.model';
import { IDesignation } from 'app/shared/model/designation.model';
import { IOffice } from 'app/shared/model/office.model';
import { DepartmentService } from 'app/entities/department';
import { DesignationService } from 'app/entities/designation';
import { OfficeService } from 'app/entities/office';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { EmployeeDetailComponent } from 'app/entities/employee';
import { IManager } from 'app/shared/model/manager.model';
import { ManagerService } from 'app/entities/manager';

@Component({
    selector: 'jhi-employee-detail',
    templateUrl: './employee-extended-detail.component.html'
})
export class EmployeeExtendedDetailComponent extends EmployeeDetailComponent implements OnInit {
    employee: IEmployee;
    department: IDepartment;
    designation: IDesignation;
    office: IOffice;
    managers: IManager[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected activatedRoute: ActivatedRoute,
        protected departmentService: DepartmentService,
        protected designationService: DesignationService,
        protected officeService: OfficeService,
        protected managerService: ManagerService
    ) {
        super(dataUtils, activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ employee }) => {
            this.employee = employee;
            this.managerService
                .query({
                    'parentEmployeeId.equals': this.employee.id
                })
                .subscribe(
                    (res: HttpResponse<IManager[]>) => {
                        this.managers = res.body;
                    },
                    (res: HttpErrorResponse) => console.log(res.message)
                );
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}

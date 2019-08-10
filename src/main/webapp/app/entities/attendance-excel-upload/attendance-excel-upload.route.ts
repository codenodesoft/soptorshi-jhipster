import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AttendanceExcelUpload } from 'app/shared/model/attendance-excel-upload.model';
import { AttendanceExcelUploadService } from './attendance-excel-upload.service';
import { AttendanceExcelUploadComponent } from './attendance-excel-upload.component';
import { AttendanceExcelUploadDetailComponent } from './attendance-excel-upload-detail.component';
import { AttendanceExcelUploadUpdateComponent } from './attendance-excel-upload-update.component';
import { AttendanceExcelUploadDeletePopupComponent } from './attendance-excel-upload-delete-dialog.component';
import { IAttendanceExcelUpload } from 'app/shared/model/attendance-excel-upload.model';

@Injectable({ providedIn: 'root' })
export class AttendanceExcelUploadResolve implements Resolve<IAttendanceExcelUpload> {
    constructor(private service: AttendanceExcelUploadService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAttendanceExcelUpload> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AttendanceExcelUpload>) => response.ok),
                map((attendanceExcelUpload: HttpResponse<AttendanceExcelUpload>) => attendanceExcelUpload.body)
            );
        }
        return of(new AttendanceExcelUpload());
    }
}

export const attendanceExcelUploadRoute: Routes = [
    {
        path: '',
        component: AttendanceExcelUploadComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_ADMIN'],
            pageTitle: 'AttendanceExcelUploads'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AttendanceExcelUploadDetailComponent,
        resolve: {
            attendanceExcelUpload: AttendanceExcelUploadResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_ADMIN'],
            pageTitle: 'AttendanceExcelUploads'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AttendanceExcelUploadUpdateComponent,
        resolve: {
            attendanceExcelUpload: AttendanceExcelUploadResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_ADMIN'],
            pageTitle: 'AttendanceExcelUploads'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AttendanceExcelUploadUpdateComponent,
        resolve: {
            attendanceExcelUpload: AttendanceExcelUploadResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_ADMIN'],
            pageTitle: 'AttendanceExcelUploads'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const attendanceExcelUploadPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AttendanceExcelUploadDeletePopupComponent,
        resolve: {
            attendanceExcelUpload: AttendanceExcelUploadResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_ADMIN'],
            pageTitle: 'AttendanceExcelUploads'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
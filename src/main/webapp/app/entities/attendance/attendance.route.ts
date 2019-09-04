import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Attendance } from 'app/shared/model/attendance.model';
import { AttendanceService } from './attendance.service';
import { AttendanceComponent } from './attendance.component';
import { AttendanceDetailComponent } from './attendance-detail.component';
import { AttendanceUpdateComponent } from './attendance-update.component';
import { AttendanceDeletePopupComponent } from './attendance-delete-dialog.component';
import { IAttendance } from 'app/shared/model/attendance.model';
import { MyAttendanceComponent } from 'app/entities/attendance/my-attendance.component';

@Injectable({ providedIn: 'root' })
export class AttendanceResolve implements Resolve<IAttendance> {
    constructor(private service: AttendanceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAttendance> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Attendance>) => response.ok),
                map((attendance: HttpResponse<Attendance>) => attendance.body)
            );
        }
        return of(new Attendance());
    }
}

export const attendanceRoute: Routes = [
    {
        path: '',
        component: AttendanceComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_MANAGER'],
            pageTitle: 'Attendances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AttendanceDetailComponent,
        resolve: {
            attendance: AttendanceResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_MANAGER'],
            pageTitle: 'Attendances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AttendanceUpdateComponent,
        resolve: {
            attendance: AttendanceResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_MANAGER'],
            pageTitle: 'Employee Attendance'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'my-attendance',
        component: MyAttendanceComponent,
        resolve: {
            attendance: AttendanceResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_MANAGER', 'ROLE_USER'],
            pageTitle: 'Attendance'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AttendanceUpdateComponent,
        resolve: {
            attendance: AttendanceResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_MANAGER'],
            pageTitle: 'Attendances'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const attendancePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AttendanceDeletePopupComponent,
        resolve: {
            attendance: AttendanceResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_MANAGER'],
            pageTitle: 'Attendances'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

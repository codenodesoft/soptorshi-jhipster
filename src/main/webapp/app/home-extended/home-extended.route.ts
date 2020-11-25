import { Route } from '@angular/router';

import { HomeExtendedComponent } from './';
import { UserRouteAccessService } from 'app/core';

export const HOME_EXTENDED_ROUTE: Route = {
    path: '',
    component: HomeExtendedComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Welcome, to Soptorshi'
    },
    canActivate: [UserRouteAccessService]
};

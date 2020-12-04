import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { ProductDeletePopupComponent, ProductResolve, ProductService } from 'app/entities/product';
import { ProductExtendedComponent } from 'app/entities/product-extended/product-extended.component';
import { ProductExtendedDetailComponent } from 'app/entities/product-extended/product-extended-detail.component';
import { ProductExtendedUpdateComponent } from 'app/entities/product-extended/product-extended-update.component';
import { ProductExtendedCategoryWiseComponent } from 'app/entities/product-extended/product-extended-category-wise.component';

@Injectable({ providedIn: 'root' })
export class ProductExtendedResolve extends ProductResolve {
    constructor(service: ProductService) {
        super(service);
    }

    /*resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProduct> {
        const id = route.params['id'] ? route.params['id'] : null;
        const productCategoryId = route.params['productCategoryId'] ? route.params['productCategoryId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Product>) => response.ok),
                map((product: HttpResponse<Product>) => product.body)
            );
        } else if (productCategoryId) {
            const product: IProduct = new Product();
            product.productCategoryId = productCategoryId;
            return of(product);
        }
        return of(new Product());
    }*/
}

export const productExtendedRoute: Routes = [
    {
        path: 'home',
        component: ProductExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Products'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':productCategoryId/home',
        component: ProductExtendedCategoryWiseComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams,
            product: ProductExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Products'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProductExtendedDetailComponent,
        resolve: {
            product: ProductExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Products'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProductExtendedUpdateComponent,
        resolve: {
            product: ProductExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Products'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':productCategoryId/new',
        component: ProductExtendedUpdateComponent,
        resolve: {
            product: ProductExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Products'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProductExtendedUpdateComponent,
        resolve: {
            product: ProductExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Products'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProductDeletePopupComponent,
        resolve: {
            product: ProductExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Products'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

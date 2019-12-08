/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyAreaManagerUpdateComponent } from 'app/entities/supply-area-manager/supply-area-manager-update.component';
import { SupplyAreaManagerService } from 'app/entities/supply-area-manager/supply-area-manager.service';
import { SupplyAreaManager } from 'app/shared/model/supply-area-manager.model';

describe('Component Tests', () => {
    describe('SupplyAreaManager Management Update Component', () => {
        let comp: SupplyAreaManagerUpdateComponent;
        let fixture: ComponentFixture<SupplyAreaManagerUpdateComponent>;
        let service: SupplyAreaManagerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyAreaManagerUpdateComponent]
            })
                .overrideTemplate(SupplyAreaManagerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SupplyAreaManagerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplyAreaManagerService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplyAreaManager(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplyAreaManager = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplyAreaManager();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplyAreaManager = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});

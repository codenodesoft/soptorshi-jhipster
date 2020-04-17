/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplySalesRepresentativeDeleteDialogComponent } from 'app/entities/supply-sales-representative/supply-sales-representative-delete-dialog.component';
import { SupplySalesRepresentativeService } from 'app/entities/supply-sales-representative/supply-sales-representative.service';

describe('Component Tests', () => {
    describe('SupplySalesRepresentative Management Delete Component', () => {
        let comp: SupplySalesRepresentativeDeleteDialogComponent;
        let fixture: ComponentFixture<SupplySalesRepresentativeDeleteDialogComponent>;
        let service: SupplySalesRepresentativeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplySalesRepresentativeDeleteDialogComponent]
            })
                .overrideTemplate(SupplySalesRepresentativeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplySalesRepresentativeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplySalesRepresentativeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});

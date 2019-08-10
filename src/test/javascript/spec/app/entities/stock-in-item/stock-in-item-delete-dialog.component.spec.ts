/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { StockInItemDeleteDialogComponent } from 'app/entities/stock-in-item/stock-in-item-delete-dialog.component';
import { StockInItemService } from 'app/entities/stock-in-item/stock-in-item.service';

describe('Component Tests', () => {
    describe('StockInItem Management Delete Component', () => {
        let comp: StockInItemDeleteDialogComponent;
        let fixture: ComponentFixture<StockInItemDeleteDialogComponent>;
        let service: StockInItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [StockInItemDeleteDialogComponent]
            })
                .overrideTemplate(StockInItemDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StockInItemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockInItemService);
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

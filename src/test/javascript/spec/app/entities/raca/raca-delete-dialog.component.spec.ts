/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CaesGatosTestModule } from '../../../test.module';
import { RacaDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/raca/raca-delete-dialog.component';
import { RacaService } from '../../../../../../main/webapp/app/entities/raca/raca.service';

describe('Component Tests', () => {

    describe('Raca Management Delete Component', () => {
        let comp: RacaDeleteDialogComponent;
        let fixture: ComponentFixture<RacaDeleteDialogComponent>;
        let service: RacaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CaesGatosTestModule],
                declarations: [RacaDeleteDialogComponent],
                providers: [
                    RacaService
                ]
            })
            .overrideTemplate(RacaDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RacaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RacaService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});

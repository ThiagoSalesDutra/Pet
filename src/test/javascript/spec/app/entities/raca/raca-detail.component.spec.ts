/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CaesGatosTestModule } from '../../../test.module';
import { RacaDetailComponent } from '../../../../../../main/webapp/app/entities/raca/raca-detail.component';
import { RacaService } from '../../../../../../main/webapp/app/entities/raca/raca.service';
import { Raca } from '../../../../../../main/webapp/app/entities/raca/raca.model';

describe('Component Tests', () => {

    describe('Raca Management Detail Component', () => {
        let comp: RacaDetailComponent;
        let fixture: ComponentFixture<RacaDetailComponent>;
        let service: RacaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CaesGatosTestModule],
                declarations: [RacaDetailComponent],
                providers: [
                    RacaService
                ]
            })
            .overrideTemplate(RacaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RacaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RacaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Raca(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.raca).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

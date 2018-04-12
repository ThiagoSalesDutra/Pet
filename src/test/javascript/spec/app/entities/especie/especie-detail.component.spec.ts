/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CaesGatosTestModule } from '../../../test.module';
import { EspecieDetailComponent } from '../../../../../../main/webapp/app/entities/especie/especie-detail.component';
import { EspecieService } from '../../../../../../main/webapp/app/entities/especie/especie.service';
import { Especie } from '../../../../../../main/webapp/app/entities/especie/especie.model';

describe('Component Tests', () => {

    describe('Especie Management Detail Component', () => {
        let comp: EspecieDetailComponent;
        let fixture: ComponentFixture<EspecieDetailComponent>;
        let service: EspecieService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CaesGatosTestModule],
                declarations: [EspecieDetailComponent],
                providers: [
                    EspecieService
                ]
            })
            .overrideTemplate(EspecieDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EspecieDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EspecieService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Especie(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.especie).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

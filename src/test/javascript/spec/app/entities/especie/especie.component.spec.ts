/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CaesGatosTestModule } from '../../../test.module';
import { EspecieComponent } from '../../../../../../main/webapp/app/entities/especie/especie.component';
import { EspecieService } from '../../../../../../main/webapp/app/entities/especie/especie.service';
import { Especie } from '../../../../../../main/webapp/app/entities/especie/especie.model';

describe('Component Tests', () => {

    describe('Especie Management Component', () => {
        let comp: EspecieComponent;
        let fixture: ComponentFixture<EspecieComponent>;
        let service: EspecieService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CaesGatosTestModule],
                declarations: [EspecieComponent],
                providers: [
                    EspecieService
                ]
            })
            .overrideTemplate(EspecieComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EspecieComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EspecieService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Especie(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.especies[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

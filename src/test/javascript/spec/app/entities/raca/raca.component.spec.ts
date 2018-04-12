/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CaesGatosTestModule } from '../../../test.module';
import { RacaComponent } from '../../../../../../main/webapp/app/entities/raca/raca.component';
import { RacaService } from '../../../../../../main/webapp/app/entities/raca/raca.service';
import { Raca } from '../../../../../../main/webapp/app/entities/raca/raca.model';

describe('Component Tests', () => {

    describe('Raca Management Component', () => {
        let comp: RacaComponent;
        let fixture: ComponentFixture<RacaComponent>;
        let service: RacaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CaesGatosTestModule],
                declarations: [RacaComponent],
                providers: [
                    RacaService
                ]
            })
            .overrideTemplate(RacaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RacaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RacaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Raca(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.racas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

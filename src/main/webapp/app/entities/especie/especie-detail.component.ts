import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Especie } from './especie.model';
import { EspecieService } from './especie.service';

@Component({
    selector: 'jhi-especie-detail',
    templateUrl: './especie-detail.component.html'
})
export class EspecieDetailComponent implements OnInit, OnDestroy {

    especie: Especie;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private especieService: EspecieService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEspecies();
    }

    load(id) {
        this.especieService.find(id)
            .subscribe((especieResponse: HttpResponse<Especie>) => {
                this.especie = especieResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEspecies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'especieListModification',
            (response) => this.load(this.especie.id)
        );
    }
}

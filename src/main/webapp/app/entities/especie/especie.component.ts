import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Especie } from './especie.model';
import { EspecieService } from './especie.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-especie',
    templateUrl: './especie.component.html'
})
export class EspecieComponent implements OnInit, OnDestroy {
especies: Especie[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private especieService: EspecieService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.especieService.query().subscribe(
            (res: HttpResponse<Especie[]>) => {
                this.especies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEspecies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Especie) {
        return item.id;
    }
    registerChangeInEspecies() {
        this.eventSubscriber = this.eventManager.subscribe('especieListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

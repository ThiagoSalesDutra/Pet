import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Raca } from './raca.model';
import { RacaService } from './raca.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-raca',
    templateUrl: './raca.component.html'
})
export class RacaComponent implements OnInit, OnDestroy {
racas: Raca[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private racaService: RacaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.racaService.query().subscribe(
            (res: HttpResponse<Raca[]>) => {
                this.racas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRacas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Raca) {
        return item.id;
    }
    registerChangeInRacas() {
        this.eventSubscriber = this.eventManager.subscribe('racaListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

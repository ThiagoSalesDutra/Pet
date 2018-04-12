import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Raca } from './raca.model';
import { RacaPopupService } from './raca-popup.service';
import { RacaService } from './raca.service';

@Component({
    selector: 'jhi-raca-dialog',
    templateUrl: './raca-dialog.component.html'
})
export class RacaDialogComponent implements OnInit {

    raca: Raca;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private racaService: RacaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.raca.id !== undefined) {
            this.subscribeToSaveResponse(
                this.racaService.update(this.raca));
        } else {
            this.subscribeToSaveResponse(
                this.racaService.create(this.raca));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Raca>>) {
        result.subscribe((res: HttpResponse<Raca>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Raca) {
        this.eventManager.broadcast({ name: 'racaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-raca-popup',
    template: ''
})
export class RacaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private racaPopupService: RacaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.racaPopupService
                    .open(RacaDialogComponent as Component, params['id']);
            } else {
                this.racaPopupService
                    .open(RacaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

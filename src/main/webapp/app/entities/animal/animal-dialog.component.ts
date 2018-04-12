import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Animal } from './animal.model';
import { AnimalPopupService } from './animal-popup.service';
import { AnimalService } from './animal.service';

@Component({
    selector: 'jhi-animal-dialog',
    templateUrl: './animal-dialog.component.html'
})
export class AnimalDialogComponent implements OnInit {

    animal: Animal;
    isSaving: boolean;
    dataNascimentoDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private animalService: AnimalService,
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
        if (this.animal.id !== undefined) {
            this.subscribeToSaveResponse(
                this.animalService.update(this.animal));
        } else {
            this.subscribeToSaveResponse(
                this.animalService.create(this.animal));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Animal>>) {
        result.subscribe((res: HttpResponse<Animal>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Animal) {
        this.eventManager.broadcast({ name: 'animalListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-animal-popup',
    template: ''
})
export class AnimalPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private animalPopupService: AnimalPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.animalPopupService
                    .open(AnimalDialogComponent as Component, params['id']);
            } else {
                this.animalPopupService
                    .open(AnimalDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

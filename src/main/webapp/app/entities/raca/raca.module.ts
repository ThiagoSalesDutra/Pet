import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CaesGatosSharedModule } from '../../shared';
import {
    RacaService,
    RacaPopupService,
    RacaComponent,
    RacaDetailComponent,
    RacaDialogComponent,
    RacaPopupComponent,
    RacaDeletePopupComponent,
    RacaDeleteDialogComponent,
    racaRoute,
    racaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...racaRoute,
    ...racaPopupRoute,
];

@NgModule({
    imports: [
        CaesGatosSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RacaComponent,
        RacaDetailComponent,
        RacaDialogComponent,
        RacaDeleteDialogComponent,
        RacaPopupComponent,
        RacaDeletePopupComponent,
    ],
    entryComponents: [
        RacaComponent,
        RacaDialogComponent,
        RacaPopupComponent,
        RacaDeleteDialogComponent,
        RacaDeletePopupComponent,
    ],
    providers: [
        RacaService,
        RacaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CaesGatosRacaModule {}

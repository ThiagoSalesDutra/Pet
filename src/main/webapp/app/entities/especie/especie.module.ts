import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CaesGatosSharedModule } from '../../shared';
import {
    EspecieService,
    EspeciePopupService,
    EspecieComponent,
    EspecieDetailComponent,
    EspecieDialogComponent,
    EspeciePopupComponent,
    EspecieDeletePopupComponent,
    EspecieDeleteDialogComponent,
    especieRoute,
    especiePopupRoute,
} from './';

const ENTITY_STATES = [
    ...especieRoute,
    ...especiePopupRoute,
];

@NgModule({
    imports: [
        CaesGatosSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EspecieComponent,
        EspecieDetailComponent,
        EspecieDialogComponent,
        EspecieDeleteDialogComponent,
        EspeciePopupComponent,
        EspecieDeletePopupComponent,
    ],
    entryComponents: [
        EspecieComponent,
        EspecieDialogComponent,
        EspeciePopupComponent,
        EspecieDeleteDialogComponent,
        EspecieDeletePopupComponent,
    ],
    providers: [
        EspecieService,
        EspeciePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CaesGatosEspecieModule {}

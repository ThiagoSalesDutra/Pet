import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CaesGatosEspecieModule } from './especie/especie.module';
import { CaesGatosRacaModule } from './raca/raca.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CaesGatosEspecieModule,
        CaesGatosRacaModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CaesGatosEntityModule {}

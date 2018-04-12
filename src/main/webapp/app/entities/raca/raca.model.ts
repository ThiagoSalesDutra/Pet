import { BaseEntity } from './../../shared';

export class Raca implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public detalhes?: string,
        public porte?: string,
        public especies?: BaseEntity[],
    ) {
    }
}

import { BaseEntity } from './../../shared';

export class Animal implements BaseEntity {
    constructor(
        public id?: number,
        public especie?: string,
        public nome?: string,
        public dataNascimento?: any,
    ) {
    }
}

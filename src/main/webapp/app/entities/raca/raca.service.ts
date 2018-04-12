import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Raca } from './raca.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Raca>;

@Injectable()
export class RacaService {

    private resourceUrl =  SERVER_API_URL + 'api/racas';

    constructor(private http: HttpClient) { }

    create(raca: Raca): Observable<EntityResponseType> {
        const copy = this.convert(raca);
        return this.http.post<Raca>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(raca: Raca): Observable<EntityResponseType> {
        const copy = this.convert(raca);
        return this.http.put<Raca>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Raca>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Raca[]>> {
        const options = createRequestOption(req);
        return this.http.get<Raca[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Raca[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Raca = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Raca[]>): HttpResponse<Raca[]> {
        const jsonResponse: Raca[] = res.body;
        const body: Raca[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Raca.
     */
    private convertItemFromServer(raca: Raca): Raca {
        const copy: Raca = Object.assign({}, raca);
        return copy;
    }

    /**
     * Convert a Raca to a JSON which can be sent to the server.
     */
    private convert(raca: Raca): Raca {
        const copy: Raca = Object.assign({}, raca);
        return copy;
    }
}

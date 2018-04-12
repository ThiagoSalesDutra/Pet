import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Especie } from './especie.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Especie>;

@Injectable()
export class EspecieService {

    private resourceUrl =  SERVER_API_URL + 'api/especies';

    constructor(private http: HttpClient) { }

    create(especie: Especie): Observable<EntityResponseType> {
        const copy = this.convert(especie);
        return this.http.post<Especie>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(especie: Especie): Observable<EntityResponseType> {
        const copy = this.convert(especie);
        return this.http.put<Especie>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Especie>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Especie[]>> {
        const options = createRequestOption(req);
        return this.http.get<Especie[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Especie[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Especie = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Especie[]>): HttpResponse<Especie[]> {
        const jsonResponse: Especie[] = res.body;
        const body: Especie[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Especie.
     */
    private convertItemFromServer(especie: Especie): Especie {
        const copy: Especie = Object.assign({}, especie);
        return copy;
    }

    /**
     * Convert a Especie to a JSON which can be sent to the server.
     */
    private convert(especie: Especie): Especie {
        const copy: Especie = Object.assign({}, especie);
        return copy;
    }
}

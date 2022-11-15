import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {RestOut, AppService} from '../app.service';

@Injectable({
  providedIn: 'root'
})
export class ProjektDetaljiService {
  fpData!: ProjektDataljiRestOut;

  constructor(private http:HttpClient, private appService: AppService) { }

  getDataSource(): RezultatMsg | null {
    if(this.fpData != null && this.fpData.rezultat != null) {
      return this.fpData.rezultat;
    }

    return null;
  }

  getConfigUrediProjektDatalji(id: number, totalRevenue: number, costPs: number) {
    return this.appService.getConfigPut(this.http, `urediProjektDatalji/${id}?totalRevenue=${totalRevenue}&costPs=${costPs}`);
  }

  getConfig(id: string) {
    return this.appService.getConfig(this.http, `projektDatalji/${id}`);
  }
}

export interface ProjektDataljiRestOut extends RestOut {
  rezultat: RezultatMsg;
}

export interface RezultatMsg {
  id: number;
  costActual: number;
  costPlanned: number;
  costPs: number;
  totalRevenue: number;
  ts: number;
  sifarnikValuta: any;
  projekt: any;
}
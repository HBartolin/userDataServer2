import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {RestOut, AppService} from '../app.service';

@Injectable({
  providedIn: 'root'
})
export class ProjectiService {
  fpData!: ProjektiRestOut

  constructor(private http:HttpClient, private appService: AppService) { }

  getDataSource(): RezultatMsg[] {
    if(this.fpData != null && this.fpData.rezultat != null) {
      return this.fpData.rezultat;
    }

    return [];
  }

  getConfig() {
    return this.appService.getConfig(this.http, "projekti?status=A");
  }

  inicijalnoNapuni() {
    return this.appService.getConfig(this.http, "createDB");
  }
}

export interface ProjektiRestOut extends RestOut {
  rezultat: RezultatMsg[];
}

export interface RezultatMsg {
  id: number;
  claim: string;
  contract: string;
  status: string;
}

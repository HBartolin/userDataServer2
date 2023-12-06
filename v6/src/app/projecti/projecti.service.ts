import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {RestOut, AppService} from '../app.service';
import {catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProjectiService {
  fpData!: ProjektiRestOut

  constructor(private http:HttpClient, private appService: AppService) { }

  pozoviVanjskiRest() {
    const reqresUrl="https://reqres.in/api/users?page=2";
    console.log(reqresUrl);
    return this.http.get(reqresUrl, { responseType:'text' }).pipe(catchError(this.mojCatchError()));
  }

  mojCatchError(): any {
    //console.log(222222222222222);
  }

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

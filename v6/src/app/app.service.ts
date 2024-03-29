import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar';
import {catchError} from 'rxjs/operators';
import {environment} from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AppService {  
  constructor() { }

  getConfig(http: HttpClient, configUrl: string) {
    let cUrl=this.logError("GET", configUrl);
    let headers = new HttpHeaders();
    headers = headers.append('Cookie', 'JSESSIONID=8C25980925F9855DA179C414691FC885');
    headers = headers.append('withCredentials', 'true');

    return http.get(cUrl, {headers: headers}).pipe(catchError(this.mojCatchError()));
  }

  getConfigPut(http: HttpClient, configUrl: string) {
    let cUrl=this.logError("PUT", configUrl);
    
    return http.put(cUrl, "").pipe(catchError(this.mojCatchError()));
  }

  logError(method: string, configUrl: string): string {
    let cUrl: string=`${environment.configUrlBegin}${configUrl}`;
    console.log(`${method} ${cUrl}`);

    return cUrl;
  }

  mojCatchError(): any {
    //console.log(222222222222222);
  }

  prikaziGreskaOk(snackBar: MatSnackBar, data: RestOut): boolean {
    let message: Array<string>=[];
    let ok: boolean=true;

    if(data.greska.length>0) {
      message=data.greska;
    } else if(data.ok.length>0) {
      message[0]=data.ok;
    }

    if(message.length>0) {
      ok=false;
      snackBar.open(message[0], "OK");
    }

    return ok;
  }

  catchBadResponse(snackBar: MatSnackBar, e: Error) {
    console.error(e);
    snackBar.open(e.toString(), "OK");
  }
}

export interface RestOut {
  greska: Array<string>;
  ok: string;
  rezultatPage: any;
}
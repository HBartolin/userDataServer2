import {Component, OnInit} from '@angular/core';
import {ProjectiService, ProjektiRestOut, RezultatMsg} from './projecti.service';
import {RestOut} from '../app.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {AppService} from '../app.service';
import {MatDialog} from '@angular/material/dialog';
import {ProjectiUrediComponent} from './projecti-uredi/projecti-uredi.component';
import {Router} from '@angular/router';

@Component({
  selector: 'app-projecti',
  templateUrl: './projecti.component.html',
  styleUrls: ['./projecti.component.css']
})
export class ProjectiComponent implements OnInit {
  displayedColumns: string[] = ['id', 'claim', 'contract', 'status' , "a" ]; 

  constructor(
    private pS: ProjectiService, 
    private snackBar: MatSnackBar, 
    private appService: AppService,
    private dialog: MatDialog,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.getConfigSubscribe();  
  }

  pozoviVanjskiRest() {
    this.pS.pozoviVanjskiRest().subscribe({
      next: (data) => {
        console.log(data);
        this.snackBar.open(data, "OK");
        //this.pS.fpData = data;
        //this.appService.prikaziGreskaOk(this.snackBar, data);
      },
      error: (e) => this.appService.catchBadResponse(this.snackBar, e)
    });
  }

  uredi(r: RezultatMsg) {
    let copyR: RezultatMsg = Object.assign({}, r);

    const dialogRef = this.dialog.open(ProjectiUrediComponent, {
      width: '250px',
      data: copyR,
    });

    dialogRef.afterClosed().subscribe(result => {      
      if(result!==undefined) {
        console.log(result);
        //http://localhost:8090/api/urediProjektDatalji/1?totalRevenue=123456&costPs=10

      }
    });
  }

  otvori(kuda: number) {
    this.router.navigate(['/projektDetalji/', kuda]);
  }

  getConfigSubscribe() {
    this.pS.getConfig().subscribe({
      next: (data: ProjektiRestOut) => {
        this.pS.fpData = data;
        this.appService.prikaziGreskaOk(this.snackBar, data);
      },
      error: (e) => this.appService.catchBadResponse(this.snackBar, e)
    });
  }

  getDataSource(): RezultatMsg[] {
    return this.pS.getDataSource();
  }

  inicijalnoNapuni() { 
    this.pS.inicijalnoNapuni().subscribe({
      next: (data: RestOut) => {
        this.getConfigSubscribe();
        this.appService.prikaziGreskaOk(this.snackBar, data);
      },
      error: (e) => this.appService.catchBadResponse(this.snackBar, e)
    });
  }

}

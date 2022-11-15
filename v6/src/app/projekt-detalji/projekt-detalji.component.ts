import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ProjektDetaljiService, ProjektDataljiRestOut, RezultatMsg} from './projekt-detalji.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {AppService} from '../app.service';
import {MatDialog} from '@angular/material/dialog';
import {ProjektDetaljiUrediComponent} from './projekt-detalji-uredi/projekt-detalji-uredi.component';

@Component({
  selector: 'app-projekt-detalji',
  templateUrl: './projekt-detalji.component.html',
  styleUrls: ['./projekt-detalji.component.css']
})
export class ProjektDetaljiComponent implements OnInit {

  constructor(
      private route: ActivatedRoute, 
      private pdS: ProjektDetaljiService, 
      private snackBar: MatSnackBar,
      private appService: AppService,
      private dialog: MatDialog,
    ) { 
  }

  ngOnInit(): void {
    let id=this.route.snapshot.params['id'];
    
    this.pdS.getConfig(id).subscribe({
      next: (data: ProjektDataljiRestOut) => {
        this.pdS.fpData = data;
        this.appService.prikaziGreskaOk(this.snackBar, data);
      },
      error: (e) => this.appService.catchBadResponse(this.snackBar, e)
    });  
  }

  uredi() {
    let copyR: RezultatMsg = Object.assign({}, this.getDataSource());
    
    const dialogRef = this.dialog.open(ProjektDetaljiUrediComponent, {
      width: '250px',
      data: copyR,
    });
  }

  getDataSource(): RezultatMsg | null {
    return this.pdS.getDataSource();
  }

}

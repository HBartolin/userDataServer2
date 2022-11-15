import {Component, OnInit, Inject} from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {RezultatMsg} from '../projekt-detalji.service';
import {NgForm} from '@angular/forms';
import {ProjektDetaljiService, ProjektDataljiRestOut} from '../projekt-detalji.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {AppService} from '../../app.service';

@Component({
  selector: 'app-projekt-detalji-uredi',
  templateUrl: './projekt-detalji-uredi.component.html',
  styleUrls: ['./projekt-detalji-uredi.component.css']
})
export class ProjektDetaljiUrediComponent implements OnInit {

  constructor(
    private dialogRef: MatDialogRef<ProjektDetaljiUrediComponent>,
    private pdS: ProjektDetaljiService,
    private snackBar: MatSnackBar,
    private appService: AppService,
    @Inject(MAT_DIALOG_DATA) public data: RezultatMsg,
  ) { }

  ngOnInit(): void { }

  onSubmit(form: NgForm) {
    const value = form.value;

    this.pdS.getConfigUrediProjektDatalji(this.data.id, value.totalRevenue, value.costPs).subscribe({
      next: (rData: ProjektDataljiRestOut) => {
        let ok: boolean=this.appService.prikaziGreskaOk(this.snackBar, rData);

        if(ok) {
          this.pdS.fpData = rData;
          this.dialogRef.close();
        } 
      },
      error: (e) => this.appService.catchBadResponse(this.snackBar, e)
    }); 
  }
  
  onNoClick(): void {
    this.dialogRef.close();
  }

}

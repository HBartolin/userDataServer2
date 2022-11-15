import {Component, OnInit, Inject} from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {RezultatMsg} from '../projecti.service';

@Component({
  selector: 'app-projecti-uredi',
  templateUrl: './projecti-uredi.component.html',
  styleUrls: ['./projecti-uredi.component.css']
})
export class ProjectiUrediComponent implements OnInit {

  constructor(
    private dialogRef: MatDialogRef<ProjectiUrediComponent>,
    @Inject(MAT_DIALOG_DATA) public data: RezultatMsg,
  ) { }

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}

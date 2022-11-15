import {NgModule} from "@angular/core";
import {MatButtonModule} from "@angular/material/button";
import {MatSliderModule} from "@angular/material/slider";
import {MatTableModule} from '@angular/material/table';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatDialogModule} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field' 
import {MatInputModule} from '@angular/material/input';

@NgModule({
  imports: [],
  exports: [
    MatButtonModule, 
    MatSliderModule, 
    MatTableModule, 
    MatSnackBarModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
  ],
})
export class MaterialModule{}
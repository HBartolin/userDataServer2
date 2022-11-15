import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ProjektDetaljiComponent} from './projekt-detalji/projekt-detalji.component';
import {ProjectiComponent} from './projecti/projecti.component';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MaterialModule} from './materialmodule';
import {ProjectiUrediComponent} from './projecti/projecti-uredi/projecti-uredi.component';
import { ProjektDetaljiUrediComponent } from './projekt-detalji/projekt-detalji-uredi/projekt-detalji-uredi.component';

@NgModule({
  declarations: [
    AppComponent,
    ProjektDetaljiComponent,
    ProjectiComponent,
    ProjectiUrediComponent,
    ProjektDetaljiUrediComponent
  ],
  imports: [
    MaterialModule,
    BrowserModule,
    AppRoutingModule,
    NoopAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

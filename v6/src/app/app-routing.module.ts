import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProjektDetaljiComponent} from './projekt-detalji/projekt-detalji.component';
import {ProjectiComponent} from './projecti/projecti.component';
import {AppComponent} from './app.component';

const routes: Routes = [
  { path: '', redirectTo: '/projekti', pathMatch: 'full' },
  { path: 'projekti', component: ProjectiComponent },
  { path: 'projektDetalji/:id', component: ProjektDetaljiComponent },
  { path: '**', component: AppComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

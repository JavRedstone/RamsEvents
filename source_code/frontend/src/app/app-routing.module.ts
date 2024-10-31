import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RankingComponent } from './components/ranking/list/ranking.component';
import { EventsComponent } from './components/events/list/events/events.component';
import { ProfileComponent } from './components/profile/profile.component';
import { ClubsComponent } from './components/clubs/list/clubs/clubs.component';
import { PrizesComponent } from './components/prizes/list/prizes/prizes.component';

const routes: Routes = [
  { path: 'rankings', component: RankingComponent },
  { path: 'events', component: EventsComponent },
  { path: 'prizes', component: PrizesComponent },
  { path: 'clubs', component: ClubsComponent },
  { path: 'profile', component: ProfileComponent },
  { path: '**', redirectTo: '', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }

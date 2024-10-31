import { NgModule, isDevMode } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { StoreRouterConnectingModule } from '@ngrx/router-store';
import { EntityDataModule } from '@ngrx/data';
import { entityConfig } from './entity-metadata';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from './modules/material.module';
import { CdkModule } from './modules/cdk.module';
import { AppFirebaseModule } from './modules/app-firebase.module';
import { MAT_RIPPLE_GLOBAL_OPTIONS, RippleGlobalOptions } from '@angular/material/core';
import { MAT_TOOLTIP_DEFAULT_OPTIONS } from '@angular/material/tooltip';
import tooltipConfig from './configs/tooltip-config';
import { CookieService } from 'ngx-cookie-service';
import { RankingComponent } from './components/ranking/list/ranking.component';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { EventsComponent } from './components/events/list/events/events.component';
import { ConfirmComponent } from './components/confirm/confirm.component';
import { EventDetailsComponent } from './components/events/details/event-details/event-details.component';
import { ProfileComponent } from './components/profile/profile.component';
import { EventAddComponent } from './components/events/add/event-add/event-add.component';
import { PrizesComponent } from './components/prizes/list/prizes/prizes.component';
import { EventUpdateComponent } from './components/events/update/event-update/event-update.component';
import { ClubsComponent } from './components/clubs/list/clubs/clubs.component';
import { ClubDetailsComponent } from './components/clubs/details/club-details/club-details.component';
import { SignupComponent } from './components/signup/signup.component';

const matRippleConfig: RippleGlobalOptions = {
  animation: {
    enterDuration: 250,
    exitDuration: 500
  }
}

/**
 * App Module
 * 
 * @author Javier Huang
 */
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    RankingComponent,
    LoginComponent,
    EventsComponent,
    ConfirmComponent,
    EventDetailsComponent,
    ProfileComponent,
    EventAddComponent,
    PrizesComponent,
    EventUpdateComponent,
    ClubsComponent,
    ClubDetailsComponent,
    SignupComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,

    FormsModule,
    ReactiveFormsModule,

    MaterialModule,
    CdkModule,

    AppFirebaseModule,

    StoreModule.forRoot({}, {}),
    // StoreModule.forFeature('rankingModuleState', reducers),
    EffectsModule.forRoot([]),
    StoreRouterConnectingModule.forRoot(),
    EntityDataModule.forRoot(entityConfig),
    StoreDevtoolsModule.instrument({ maxAge: 25, logOnly: !isDevMode() })
  ],
  providers: [
    {
      provide: MAT_RIPPLE_GLOBAL_OPTIONS,
      useValue: matRippleConfig
    },
    {
      provide: MAT_TOOLTIP_DEFAULT_OPTIONS,
      useValue: tooltipConfig
    },
    CookieService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

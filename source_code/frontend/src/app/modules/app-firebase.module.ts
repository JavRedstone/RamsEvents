import { NgModule } from '@angular/core';
import { AngularFireModule } from '@angular/fire/compat';
import { AngularFireAuthModule } from '@angular/fire/compat/auth';
import firebaseConfig from '../configs/firebase-config';

/**
 * Firebase Module
 * 
 * @author Javier Huang
 */
@NgModule({
  imports: [AngularFireModule.initializeApp(firebaseConfig)],
  exports: [AngularFireModule, AngularFireAuthModule],
})
export class AppFirebaseModule { }

import { Component } from '@angular/core';
import { ILoginBaseInfo } from 'src/app/interfaces/login/login-interface';
import { LoginComponent } from '../login/login.component';

/**
 * Profile Component
 * 
 * @author Javier Huang
 */
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {
  public currentUser: ILoginBaseInfo = LoginComponent.currentUser;
}

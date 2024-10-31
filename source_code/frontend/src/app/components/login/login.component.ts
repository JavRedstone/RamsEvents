import { Component } from '@angular/core';
import { EMPTY, catchError, lastValueFrom, take } from 'rxjs';
import { AppComponent } from 'src/app/app.component';
import firebase from 'firebase/compat/app';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import snackbarConfig from 'src/app/configs/snackbar-config';
import { LoginService } from 'src/app/services/login/login.service';
import { ILoginBaseInfo, ILoginSearch } from 'src/app/interfaces/login/login-interface';
import { SignupComponent } from '../signup/signup.component';
import { MatDialog } from '@angular/material/dialog';

/**
 * Login Component
 * 
 * @author Javier Huang
 */
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  public errorMessage: string = '';

  public static currentUser: ILoginBaseInfo = null;
  public static signupUser: firebase.User = null;

  constructor(
    private authService: AuthService,
    private loginService: LoginService,
    private router: Router,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {
    this.setUser();
  };

  /**
   * Sets the auto-login user
   */
  private setUser(): void {
    this.authService.user$.subscribe(
      async ( user: firebase.User ) => {
        await this.setCurrentUser(user, true);
      }
    );
  }

  /**
   * Makes the user login and redirects them to the rankings page once they do
   */
  public login(): void {
    this.authService.loginViaGoogle().pipe(take(1), catchError(
      (err: any) => {
        return EMPTY;
      }
    )).subscribe(
      async ( userCredential: firebase.auth.UserCredential ) => {
        if (await this.setCurrentUser(userCredential.user, false)) {
          AppComponent.redirect('/rankings', {}, this.router);
        }
      }
    );
  }

  /**
   * Logs the user out and reloads
   * @param reload boolean
   */
  public logout(reload: boolean): void {
    this.authService.logout().pipe(take(1)).subscribe(
      async () => {
        if (reload) {
          await AppComponent.redirectWithReload('/', {}, this.router);
        }
      }
    );
  }

  /**
   * Gets the currentUser
   * @returns ILoginBaseInfo
   */
  public getCurrentUser(): ILoginBaseInfo {
    return LoginComponent.currentUser;
  }

  /**
   * Builds the search criteria for the login
   * @param user firebase.User
   * @returns ILoginSearch
   */
  private buildSearchCriteria(user: firebase.User): ILoginSearch {
    return {
      email: user.email
    };
  }

  /**
   * Sets the current user
   * @param user firebase.User
   * @param auto boolean
   */
  private async setCurrentUser(user: firebase.User, auto: boolean): Promise<boolean> {
    if (user != null) {
      if (user.emailVerified) {
        if (user.email.split('@')[1] == 'ycdsbk12.ca' || user.email.split('@')[1] == 'ycdsb.ca' || user.email.split('@')[1] == 'gmail.com') {
          let message: any = await lastValueFrom(this.loginService.login(this.buildSearchCriteria(user)));
          if (!message.status.success) {
            this.errorMessage = "An unknown error occurred";
          }
          else if (message.body.length > 0) {
            LoginComponent.currentUser = message.body[0];
          }
          else if (!auto) {
            LoginComponent.signupUser = user;
            this.dialog.open(SignupComponent);
          }
          this.errorMessage = '';
        }
        else {
          this.errorMessage = `Email ${user.email} is not part of ycdsbk12.ca or ycdsb.ca`;
        }
      }
      else {
        this.errorMessage = `Email ${user.email} is not verified`;
      }
    }
    if (this.errorMessage != '') {
      this.snackBar.open(this.errorMessage, 'Close', snackbarConfig);
      setTimeout(() => {
      }, snackbarConfig.duration);
      this.logout(false);
      
      return false;
    }
    return true;
  }
  
  /**
   * Redirects to the specified url
   * @param url string
   */
  public redirectTo(url: string): void {
    AppComponent.redirect(url, {}, this.router);
  }
}

import { Component, Inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { IloginSignup as ILoginSignup } from 'src/app/interfaces/login/login-interface';
import { LoginComponent } from '../login/login.component';
import snackbarConfig from 'src/app/configs/snackbar-config';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoginService } from 'src/app/services/login/login.service';
import { lastValueFrom } from 'rxjs';

/**
 * Sign Up Component
 * 
 * @author Javier Huang
 */
@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent {

  public signupFormGroup: FormGroup = this.fb.group({
    firstName: [''],
    middleName: [''],
    lastName: [''],
    studentId: [''],
    grade: [''],
    homeroom: ['']
  });

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private loginService: LoginService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar
  ) {
  }

  /**
   * Builds the signup criteria
   * @returns ILoginSignup
   */
  private buildSignupCriteria(): ILoginSignup {
    return {
      uid: LoginComponent.signupUser.uid,
      studentId: this.signupFormGroup.get('studentId').value,
      firstName: this.signupFormGroup.get('firstName').value,
      middleName: this.signupFormGroup.get('middleName').value,
      lastName: this.signupFormGroup.get('lastName').value,
      email: LoginComponent.signupUser.email,
      grade: this.signupFormGroup.get('grade').value,
      homeroom: this.signupFormGroup.get('homeroom').value,
      accessLevel: 'low'
    };
  }
  
  /**
   * Signs the new user up
   */
  public async signup(): Promise<void> {
    let message: any = await lastValueFrom(this.loginService.signup(this.buildSignupCriteria()));
    if (!message.status.success) {
      this.snackBar.open('An unknown error occurred', 'Close', snackbarConfig);
    }
    else {
      this.snackBar.open('You have successfully signed up to Rams Events!', 'Close', snackbarConfig);
    }
    setTimeout(() => {
      location.reload();
    }, snackbarConfig.reloadDuration);
  }

  get firstName(): FormControl {  
    return this.signupFormGroup.get('firstName') as FormControl;
  }

  get middleName(): FormControl {
    return this.signupFormGroup.get('middleName') as FormControl;
  }
  
  get lastName(): FormControl {
    return this.signupFormGroup.get('lastName') as FormControl;
  }

  get studentId(): FormControl {
    return this.signupFormGroup.get('studentId') as FormControl;
  }

  get grade(): FormControl {
    return this.signupFormGroup.get('grade') as FormControl;
  }

  get homeroom(): FormControl {
    return this.signupFormGroup.get('homeroom') as FormControl;
  }
}

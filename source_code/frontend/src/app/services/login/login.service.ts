import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BaseService } from '../communication/base.service';
import { IloginSignup, ILoginSearch } from 'src/app/interfaces/login/login-interface';

/**
 * Login Service
 * 
 * @author Javier Huang
 */
@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(
    private communicationService: BaseService
  ) { }

  /**
   * Logs the user in
   * @param data ILoginSearch
   * @returns Observable<any>
   */
  public login(data: ILoginSearch): Observable<any> {
    return this.communicationService.post('login/login', data);
  }

  /**
   * Signs the user up
   * @param data ILoginSignup
   * @returns Observable<any>
   */
  public signup(data: IloginSignup): Observable<any> {
    return this.communicationService.post('login/signup', data);
  }
}

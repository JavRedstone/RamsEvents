import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { LoginComponent } from './components/login/login.component';
import { ILoginBaseInfo } from './interfaces/login/login-interface';
import { environment } from 'src/environments/environment';

/**
 * App Component
 * 
 * @author Javier Huang
 */
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  public isDark: boolean = false;
  public iconOnly: boolean = false;

  constructor(
    public router: Router,
    private cookieService: CookieService
  ) {
    this.getIsDark();
    setInterval(() => {
      if (LoginComponent.currentUser == null && LoginComponent.signupUser != null && location.href.split('/')[3] != null) {
        this.redirectTo('');
      }
    }, 100);
  }

  private getIsDark(): void {
    let isDark: string = this.cookieService.getAll()['isDark'];
    this.isDark = isDark == 'false' ? false : true;
  }

  public darkLight(): void {
    this.isDark = !this.isDark;
    this.cookieService.set('isDark', `${this.isDark}`,  new Date(2147483647 * 1000), '/');
  }

  public getCurrentUser(): ILoginBaseInfo {
    return LoginComponent.currentUser;
  }

  public redirectTo(url: string): void {
    AppComponent.redirect(url, {}, this.router);
  }

  public isBasePath(path: string): boolean {
    return location.href == environment.frontendContextPath + path;
  }

  public static async redirect(path: string, queryParams: any = {}, router: Router): Promise<boolean> {
    await router.navigate([path], {
      queryParams: queryParams
    });
    return true;
  }

  public static async redirectWithReload(path: string, queryParams: any = {}, router: Router): Promise<boolean> {
    await router.navigate([path], {
      queryParams: queryParams
    });
    location.reload();
    return true;
  }

  public getAuth(): boolean {
    return LoginComponent.currentUser != null;
  }
}

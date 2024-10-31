import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from 'src/app/app.component';
import { LoginComponent } from '../login/login.component';

/**
 * Home Component
 * 
 * @author Javier Huang
 */
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  constructor(
    private router: Router
  ) {}

  /**
   * Redirects to the path
   * @param path string
   */
  public async redirect(path: string): Promise<void> {
    await AppComponent.redirect(path, {}, this.router);
  }

  /**
   * Gets the user authentication status
   */
  public getAuth(): boolean {
    return LoginComponent.currentUser != null;
  }
}

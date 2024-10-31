import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of as observableOf } from 'rxjs';
import { retry, switchMap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

/**
 * Base Service
 * 
 * @author Javier Huang
 */
@Injectable({
  providedIn: 'root'
})
export class BaseService {

  public baseUrl = environment.apiBaseUrl;

  constructor(private httpClient: HttpClient) { }

  /**
   * Performs the get function
   * @param serviceUrl string
   * @returns Observable<any>
   */
  private doGet(serviceUrl: string): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + serviceUrl);
  }

  /**
   * Performs the post function
   * @param serviceUrl string
   * @param payload any
   * @returns Observable<any>
   */
  private doPost(serviceUrl: string, payload: any): Observable<any> {
    return this.httpClient.post<any>(this.baseUrl + serviceUrl, payload);
  }
  
  /**
   * Calls the get function
   * @param url string
   * @returns Observable<any>
   */
  public get(url: string): Observable<any> {
    return this.doGet(url).pipe(
      switchMap(response => observableOf(response)),
      retry(2)
    );
  }

  /**
   * Calls the post function
   * @param url string
   * @param payload any
   * @returns 
   */
  public post(url: string, payload: any): Observable<any> {
    return this.doPost(url, payload).pipe(
      switchMap(response => observableOf(response))
    )
  }
}

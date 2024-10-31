import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BaseService } from '../communication/base.service';
import { IClubsSearch } from 'src/app/interfaces/clubs/clubs-interface';

/**
 * Clubs Service
 * 
 * @author Javier Huang
 */
@Injectable({
  providedIn: 'root'
})
export class ClubsService {

  constructor(
    private communicationService: BaseService
  ) { }
  
  /**
   * Searches for the clubs
   * @param data IClubsSearch
   * @returns Observable<any>
   */
  public getClubsSearch(data: IClubsSearch): Observable<any> {
    return this.communicationService.post('clubs/search', data);
  }
}

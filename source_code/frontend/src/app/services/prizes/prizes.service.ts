import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IPrizesSearch } from 'src/app/interfaces/prizes/prizes-interface';
import { BaseService } from '../communication/base.service';

/**
 * Prizes Service
 * 
 * @author Javier Huang
 */
@Injectable({
  providedIn: 'root'
})
export class PrizesService {

  constructor(
    private communicationService: BaseService
  ) { }

  /**
   * Searches for the prizes
   * @param data IPrizesSearch
   * @returns Observable<any>
   */
  public getPrizesSearch(data: IPrizesSearch): Observable<any> {
    return this.communicationService.post('prizes/search', data);
  }
}

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IGrantPrizes, IRankingSearch } from 'src/app/interfaces/ranking/ranking-interface';
import { BaseService } from '../communication/base.service';

/**
 * Ranking Service
 * 
 * @author Javier Huang
 */
@Injectable({
  providedIn: 'root'
})
export class RankingService {
  constructor(
    private communicationService: BaseService
  ) { }

  /**
   * 
   * @param data IRankingSearch
   * @returns Observable<any>
   */
  public getRankingSearch(data: IRankingSearch): Observable<any> {
    return this.communicationService.post('rankings/search', data);
  }

  /**
   * 
   * @param data IGrantPrizes
   * @returns Observable<any>
   */
  public grantPrizes(data: IGrantPrizes): Observable<any> {
    return this.communicationService.post('rankings/grantprizes', data);    
  }
}

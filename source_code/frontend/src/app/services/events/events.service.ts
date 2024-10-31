import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BaseService } from '../communication/base.service';
import { IEventsAdd, IEventsDelete, IEventsActions, IEventsJoinedSearch, IEventsSearch, IEventsUpdate } from 'src/app/interfaces/events/events-interface';

/**
 * Events Service
 * 
 * @author Javier Huang
 */
@Injectable({
  providedIn: 'root'
})
export class EventsService {

  constructor(
    private communicationService: BaseService
  ) { }

  /**
   * Gets the event search
   * @param data IEventsSearch
   * @returns Observable<any>
   */
  public getEventsSearch(data: IEventsSearch): Observable<any> {
    return this.communicationService.post('events/search', data);
  }

  /**
   * Gets the joined events search
   * @param data IEventsJoinedSearch
   * @returns Observable<any>
   */
  public getJoinedEventsSearch(data: IEventsJoinedSearch): Observable<any> {
    return this.communicationService.post('events/searchjoined', data);
  }

  /**
   * Joins the event
   * @param data IEventsActions
   * @returns Observable<any>
   */
  public joinEvent(data: IEventsActions): Observable<any> {
    return this.communicationService.post('events/join', data);
  }

  /**
   * Leaves the event
   * @param data IEventsActions
   * @returns Observable<any>
   */
  public leaveEvent(data: IEventsActions): Observable<any> {
    return this.communicationService.post('events/leave', data);
  }

  /**
   * Adds the event
   * @param data IEventsAdd
   * @returns Observable<any>
   */
  public addEvent(data: IEventsAdd): Observable<any> {
    return this.communicationService.post('events/add', data);
  }

  /**
   * Updates the event
   * @param data IEventsUpdate
   * @returns Observable<any>
   */
  public updateEvent(data: IEventsUpdate): Observable<any> {
    return this.communicationService.post('events/update', data);
  }

  /**
   * Gets events joined students
   * @param data IEventsDelete
   * @returns Observable<any>
   */
  public getJoinedStudents(data: IEventsDelete): Observable<any> {
    return this.communicationService.post('events/getJoined', data);
  }

  /**
   * Deletes the event
   * @param data IEventsDelete
   * @returns Observable<any>
   */
  public deleteEvent(data: IEventsDelete): Observable<any> {
    return this.communicationService.post('events/delete', data);
  }
}

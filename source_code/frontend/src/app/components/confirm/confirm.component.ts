import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { IEventsDelete, IEventsActions, IEventsStudents } from 'src/app/interfaces/events/events-interface';
import { EventsService } from 'src/app/services/events/events.service';
import { LoginComponent } from '../login/login.component';
import { lastValueFrom } from 'rxjs';
import snackbarConfig from 'src/app/configs/snackbar-config';
import { AppComponent } from 'src/app/app.component';
import { Router } from '@angular/router';

/**
 * Confirm Component
 * 
 * @author Javier Huang
 */
@Component({
  selector: 'app-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.scss']
})
export class ConfirmComponent {
  public deleteEventError: boolean = false;
  public deleteEventErrorStudents: IEventsStudents[] = [];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,

    private eventsService: EventsService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {
    this.initialCheck();
  }

  private async initialCheck(): Promise<void> {
    if (this.data.type == 'Delete Event') {
      let messageStudents: any = await lastValueFrom(this.eventsService.getJoinedStudents(this.buildDeleteCriteria()));
      if (!messageStudents.status.success) {
        this.snackBar.open('An unknown error occurred.', 'Close', snackbarConfig);
      }
      else if (messageStudents.body.length > 0) {
        this.deleteEventError = true;
        this.deleteEventErrorStudents = messageStudents.body;
      }
    }
  }

  /**
   * Builds the actions criteria for the events
   * @returns IEventsActions
   */
  private buildActionsCriteria(): IEventsActions {
    return {
      eid: this.data.eid,
      sid: LoginComponent.currentUser.sid,
      schoolYear: this.data.schoolYear,
      quarter: this.data.quarter
    };
  }

  /**
   * Builds the delete criteria for the events
   * @returns IEventsDelete
   */
  private buildDeleteCriteria(): IEventsDelete {
    return {
      eid: this.data.eid
    };
  }

  /**
   * Joins the event for the user
   */
  public async joinEvent(): Promise<void> {
    let message: any = await lastValueFrom(this.eventsService.joinEvent(this.buildActionsCriteria()));
    if (!message.status.success) {
      let errorStr: string = '';
      if (message.status.errorCodeStr == 'Missing search criteria: eid or sid') {
        errorStr = 'The event that you are looking for does not exist.';
      }
      else if (message.status.errorCodeStr == 'Student cannot join the same Event twice') {
        errorStr = 'You cannot join the same event twice!';
      }
      else {
        errorStr = 'An unknown error occurred.';
      }
      this.snackBar.open(errorStr, 'Close', snackbarConfig);
    }
    else {
      this.snackBar.open('You have successfully joined this event!', 'Close', snackbarConfig);
    }
    setTimeout(() => {
      location.reload();
    }, snackbarConfig.reloadDuration);
  }

  /**
   * Leaves the event for the user
   */
  public async leaveEvent(): Promise<void> {
    let message: any = await lastValueFrom(this.eventsService.leaveEvent(this.buildActionsCriteria()));
    if (!message.status.success) {
      let errorStr: string = '';
      if (message.status.errorCodeStr == 'Missing search criteria: eid or sid') {
        errorStr = 'The event that you are looking for does not exist.';
      }
      else {
        errorStr = 'An unknown error occurred.';
      }
      this.snackBar.open(errorStr, 'Close', snackbarConfig);
    }
    else if (message.body == 0) {
      this.snackBar.open('You have already left this event!', 'Close', snackbarConfig);
    }
    else {
      this.snackBar.open('You have successfully left this event!', 'Close', snackbarConfig);
    }
    setTimeout(() => {
      location.reload();
    }, snackbarConfig.reloadDuration);
  }

  /**
   * Adds the event for the user
   */
  public async addEvent(): Promise<void> {
    let message: any = await lastValueFrom(this.eventsService.addEvent(this.data.event));
    if (!message.status.success) {
      this.snackBar.open('An unknown error occurred', 'Close', snackbarConfig);
    }
    else {
      this.snackBar.open('You have successfully added this event!', 'Close', snackbarConfig);
    }
    setTimeout(() => {
      AppComponent.redirectWithReload('/events', {}, this.router);
    }, snackbarConfig.reloadDuration);
  }

  /**
   * Updates the event for the user
   */
  public async updateEvent(): Promise<void> {
    let message: any = await lastValueFrom(this.eventsService.updateEvent(this.data.event));
    if (!message.status.success) {
      this.snackBar.open('An unknown error occurred', 'Close', snackbarConfig);
    }
    else {
      this.snackBar.open('You have successfully updated this event!', 'Close', snackbarConfig);
    }
    setTimeout(() => {
      location.reload();
    }, snackbarConfig.reloadDuration);
  }

  /** 
   * Deletes the event for the user
   */
  public async deleteEvent(): Promise<void> {
    if (!this.deleteEventError) {
      let messageDelete: any = await lastValueFrom(this.eventsService.deleteEvent(this.buildDeleteCriteria()));
      if (!messageDelete.status.success) {
        let errorStr: string = '';
        if (messageDelete.status.errorCodeStr == 'Missing search criteria: eid') {
          errorStr = 'The event that you are looking for does not exist.';
        }
        else {
          errorStr = 'An unknown error occurred.';
        }
        this.snackBar.open(errorStr, 'Close', snackbarConfig);
      }
      else if (messageDelete.body == 0) {
        this.snackBar.open('You have already deleted this event!', 'Close', snackbarConfig);
      }
      else {
        this.snackBar.open('You have successfully deleted this event!', 'Close', snackbarConfig);
      }
      
      setTimeout(() => {
        location.reload();
      }, snackbarConfig.reloadDuration);
    }
  }
}

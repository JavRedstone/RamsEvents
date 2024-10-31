import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { ConfirmComponent } from 'src/app/components/confirm/confirm.component';

/**
 * Event Details Component
 * 
 * @author Javier Huang
 */
@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.scss']
})
export class EventDetailsComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialog: MatDialog
  ) {

  }

  /**
   * Opens the ConfirmComponent to confirm if the user wants to join the event
   */
  public joinEvent(): void {
    this.dialog.open(ConfirmComponent, {
      data: {
        type: 'Join Event',
        message: 'Are you sure you want to join this event?'
      }
    });
  }

  /**
   * Opens the ConfirmComponent to confirm if hte user wants to leave the event
   */
  public leaveEvent(): void {
    this.dialog.open(ConfirmComponent, {
      data: {
        type: 'Leave Event',
        message: 'Are you sure you want to leave this event?',
        eid: this.data.event.eid
      }
    });
  }
}

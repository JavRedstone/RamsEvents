import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { EventAddComponent } from 'src/app/components/events/add/event-add/event-add.component';

/**
 * Club Details Component
 * 
 * @author Javier Huang
 */
@Component({
  selector: 'app-club-details',
  templateUrl: './club-details.component.html',
  styleUrls: ['./club-details.component.scss']
})
export class ClubDetailsComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialog: MatDialog
  ) {

  }

  /**
   * Opens the EventAddComponent dialog
   */
  public addEvent(): void {
    this.dialog.open(EventAddComponent, {
      data: {
        cid: this.data.club.id
      }
    });
  }
}

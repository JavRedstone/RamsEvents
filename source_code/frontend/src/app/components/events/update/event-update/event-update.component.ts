import { Component, Inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { ConfirmComponent } from 'src/app/components/confirm/confirm.component';
import dateFormat from "dateformat";
import { IClubsBaseInfo } from 'src/app/interfaces/clubs/clubs-interface';
import { IEventsUpdate } from 'src/app/interfaces/events/events-interface';
import { environment } from 'src/environments/environment';

/**
 * Update Event Component
 * 
 * @author Javier Huang
 */
@Component({
  selector: 'app-event-update',
  templateUrl: './event-update.component.html',
  styleUrls: ['./event-update.component.scss']
})
export class EventUpdateComponent {
  public years: string[] = ['2021-2022', '2022-2023'];
  public quarters: number[] = [1, 2, 3, 4];
  public clubs: IClubsBaseInfo[] = [];

  public yearOption: string = this.data.event.schoolYear;
  public quarterOption: number = this.data.event.quarter;

  public updateFormGroup: FormGroup = this.fb.group({
    name: [this.data.event.eventName],
    description: [this.data.event.description],
    minPoints: [this.data.event.minPoints],
    maxPoints: [this.data.event.maxPoints],
    startDate: [this.data.event.startDate],
    endDate: [this.data.event.endDate]
  });

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private dialog: MatDialog
  ) {
  }

  /**
   * Builds the update criteria
   * @returns IEventsUpdate
   */
  private buildUpdateCriteria(): IEventsUpdate {
    return {
      eid: this.data.event.eid,
      name: this.updateFormGroup.get('name').value,
      description: this.updateFormGroup.get('description').value,
      minPoints: this.updateFormGroup.get('minPoints').value,
      maxPoints: this.updateFormGroup.get('maxPoints').value,
      startDate: dateFormat(this.updateFormGroup.get('startDate').value, environment.dateFormat),
      endDate: dateFormat(this.updateFormGroup.get('endDate').value, environment.dateFormat),
      schoolYear: this.yearOption,
      quarter: this.quarterOption
    };
  }
  
  /**
   * Opens the ConfirmComponent that confirms if the user wants to update the event
   */
  public updateEvent() {
    this.dialog.open(ConfirmComponent, {
      data: {
        type: 'Update Event',
        message: 'Are you sure you want to update this event?',
        event: this.buildUpdateCriteria()
      }
    });
  }

  get name(): FormControl {
    return this.updateFormGroup.get('name') as FormControl;
  }

  get description(): FormControl {
    return this.updateFormGroup.get('description') as FormControl;
  }

  get rules(): FormControl {
    return this.updateFormGroup.get('rules') as FormControl;
  }
  
  get minPoints(): FormControl {
    return this.updateFormGroup.get('minPoints') as FormControl;
  }

  get maxPoints(): FormControl {
    return this.updateFormGroup.get('maxPoints') as FormControl;
  }

  get startDate(): FormControl {
    return this.updateFormGroup.get('startDate') as FormControl;
  }

  get endDate(): FormControl {
    return this.updateFormGroup.get('endDate') as FormControl;
  }

  /**
   * Compares two dates to see if the first is after the second
   * @param val1 string
   * @param val2 string
   * @returns if the first date is later than the second date
   */
   public isAfter(val1: string, val2: string): boolean {
    return new Date(val1) > new Date(val2);
  }

  /**
   * Compares the number to the threshold given
   * @param val string
   * @param threshold number
   * @returns if the value is passing the threshold
   */
  public isGreaterThan(val: string, threshold: number): boolean {
    return parseInt(val) >= threshold;
  }

  /**
   * Compares the two numbers to see if the first one is greater than the second
   * @param val1 string
   * @param val2 string
   * @returns if the first value is greater than the second
   */
  public isGreater(val1: string, val2: string): boolean {
    return parseInt(val1) > parseInt(val2);
  }
}

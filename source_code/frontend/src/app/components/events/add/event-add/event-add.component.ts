import { Component, Inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { ConfirmComponent } from 'src/app/components/confirm/confirm.component';
import dateFormat from "dateformat";
import { IClubsBaseInfo } from 'src/app/interfaces/clubs/clubs-interface';
import { IEventsAdd } from 'src/app/interfaces/events/events-interface';
import { environment } from 'src/environments/environment';

/**
 * Add Event Component
 * 
 * @author Javier Huang
 */
@Component({
  selector: 'app-event-add',
  templateUrl: './event-add.component.html',
  styleUrls: ['./event-add.component.scss']
})
export class EventAddComponent {
  public years: string[] = ['2021-2022', '2022-2023'];
  public quarters: number[] = [1, 2, 3, 4];
  public clubs: IClubsBaseInfo[] = [];

  public yearOption: string = this.years[1];
  public quarterOption: number = this.quarters[2];

  public addFormGroup: FormGroup = this.fb.group({
    name: [''],
    description: [''],
    minPoints: [''],
    maxPoints: [''],
    startDate: [''],
    endDate: ['']
  });

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private dialog: MatDialog
  ) {}

  /**
   * Builds the criteria necessary to add events
   * @returns IEventsAdd
   */
  private buildAddCriteria(): IEventsAdd {
    return {
      name: this.addFormGroup.get('name').value,
      description: this.addFormGroup.get('description').value,
      minPoints: this.addFormGroup.get('minPoints').value,
      maxPoints: this.addFormGroup.get('maxPoints').value,
      startDate: dateFormat(this.addFormGroup.get('startDate').value, environment.dateFormat),
      endDate: dateFormat(this.addFormGroup.get('endDate').value, environment.dateFormat),
      cid: this.data.cid,
      schoolYear: this.yearOption,
      quarter: this.quarterOption
    };
  }
  
  /**
   * Opens the ConfirmComponent to confirm adding the event
   */
  public addEvent() {
    this.dialog.open(ConfirmComponent, {
      data: {
        type: 'Add Event',
        message: 'Are you sure you want to add this event?',
        event: this.buildAddCriteria()
      }
    });
  }

  get name(): FormControl {
    return this.addFormGroup.get('name') as FormControl;
  }

  get description(): FormControl {
    return this.addFormGroup.get('description') as FormControl;
  }
  
  get minPoints(): FormControl {
    return this.addFormGroup.get('minPoints') as FormControl;
  }

  get maxPoints(): FormControl {
    return this.addFormGroup.get('maxPoints') as FormControl;
  }

  get startDate(): FormControl {
    return this.addFormGroup.get('startDate') as FormControl;
  }

  get endDate(): FormControl {
    return this.addFormGroup.get('endDate') as FormControl;
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

import { Component, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { lastValueFrom } from 'rxjs';
import { EVENTS_SELECT_OPTIONS, IEventsBaseInfo, IEventsJoinedSearch, IEventsSearch } from 'src/app/interfaces/events/events-interface';
import { EventsService } from 'src/app/services/events/events.service';
import dateFormat from "dateformat";
import { environment } from 'src/environments/environment';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmComponent } from 'src/app/components/confirm/confirm.component';
import { EventDetailsComponent } from '../../details/event-details/event-details.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import snackbarConfig from 'src/app/configs/snackbar-config';
import { LoginComponent } from 'src/app/components/login/login.component';
import { MatButtonToggleChange } from '@angular/material/button-toggle';
import { EventUpdateComponent } from '../../update/event-update/event-update.component';
import { ILoginBaseInfo } from 'src/app/interfaces/login/login-interface';

/**
 * Events Component
 * 
 * @author Javier Huang
 */
@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss']
})
export class EventsComponent {
  public SEARCH_OPTIONS = [
    { description: 'All', value: EVENTS_SELECT_OPTIONS.ALL },
    { description: 'Event Name', value: EVENTS_SELECT_OPTIONS.EVENT_NAME },
    { description: 'Minimum Points', value: EVENTS_SELECT_OPTIONS.MIN_POINTS },
    { description: 'Club Name', value: EVENTS_SELECT_OPTIONS.CLUB_NAME },
    { description: 'End Date', value: EVENTS_SELECT_OPTIONS.END_DATE }
  ];
  public COLUMN_NAMES: string[] = [ 'Event Name', 'Description', 'Min Points', 'Max Points', 'Start Date', 'End Date', 'Club Name', 'Actions', LoginComponent.currentUser.accessLevel != 'high' ? 'Joined' : 'a', LoginComponent.currentUser.accessLevel == 'medium' ? 'Host' : 'b' ];
  public COLUMN_DEF: string[] = [ 'eventName', 'description', 'minPoints', 'maxPoints', 'startDate', 'endDate', 'clubName', 'actions', LoginComponent.currentUser.accessLevel != 'high' ? 'joined' : 'a', LoginComponent.currentUser.accessLevel == 'medium' ? 'owned' : 'b' ];
  public COLUMNS = LoginComponent.currentUser.accessLevel != 'low' ? [
    { def: this.COLUMN_DEF[0], name: this.COLUMN_NAMES[0] },
    { def: this.COLUMN_DEF[1], name: this.COLUMN_NAMES[1] },
    { def: this.COLUMN_DEF[2], name: this.COLUMN_NAMES[2] },
    { def: this.COLUMN_DEF[3], name: this.COLUMN_NAMES[3] },
    { def: this.COLUMN_DEF[4], name: this.COLUMN_NAMES[4] },
    { def: this.COLUMN_DEF[5], name: this.COLUMN_NAMES[5] },
    { def: this.COLUMN_DEF[6], name: this.COLUMN_NAMES[6] },
    { def: this.COLUMN_DEF[7], name: this.COLUMN_NAMES[7] },
    { def: this.COLUMN_DEF[8], name: this.COLUMN_NAMES[8] },
    { def: this.COLUMN_DEF[9], name: this.COLUMN_NAMES[9] }
  ] : [
    { def: this.COLUMN_DEF[0], name: this.COLUMN_NAMES[0] },
    { def: this.COLUMN_DEF[1], name: this.COLUMN_NAMES[1] },
    { def: this.COLUMN_DEF[2], name: this.COLUMN_NAMES[2] },
    { def: this.COLUMN_DEF[3], name: this.COLUMN_NAMES[3] },
    { def: this.COLUMN_DEF[4], name: this.COLUMN_NAMES[4] },
    { def: this.COLUMN_DEF[5], name: this.COLUMN_NAMES[5] },
    { def: this.COLUMN_DEF[6], name: this.COLUMN_NAMES[6] },
    { def: this.COLUMN_DEF[7], name: this.COLUMN_NAMES[7] },
    { def: this.COLUMN_DEF[8], name: this.COLUMN_NAMES[8] }
  ];
  public eventsInfoList!: MatTableDataSource<IEventsBaseInfo>;
  public eventsInfoListData: IEventsBaseInfo[] = [];
  public joinedEventsIds: number[] = [];
  public ownedEventsIds: number[] = [];

  @ViewChild(MatPaginator) private paginator!: MatPaginator;
  @ViewChild(MatSort) private sort!: MatSort;

  public years: string[] = ['2021-2022', '2022-2023'];
  public quarters: number[] = [1, 2, 3, 4];

  public searchYear: string = this.years[1];
  public searchQuarter: number = this.quarters[2];
  public searchOption: string = this.SEARCH_OPTIONS[0].value;

  public searchFormGroup: FormGroup = this.fb.group({
    eventName: [''],
    minPoints: [''],
    clubName: [''],
    endDate: ['']
  });

  public currentUser: ILoginBaseInfo = LoginComponent.currentUser;

  public toggleJoinedValue: string = 'all';
  public toggleOwnedValue: string = 'all';

  private toggleJoinedEvent!: MatButtonToggleChange;
  private toggleOwnedEvent!: MatButtonToggleChange;

  constructor (
    public fb: FormBuilder,
    private eventsService: EventsService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {
    this.onSubmit();
    let newColumns: string[] = [];
    for (let columnName of this.COLUMN_NAMES) {
      if (columnName == 'a' || columnName == 'b') {
        continue;
      }
      newColumns.push(columnName);
    }
    let newColumnsS: string[] = [];
    for (let columnS of this.COLUMN_DEF) {
      if (columnS == 'a' || columnS == 'b') {
        continue;
      }
      newColumnsS.push(columnS);
    }
    this.COLUMN_NAMES = newColumns;
    this.COLUMN_DEF = newColumnsS;
  }

  /**
   * Builds the search criteria for the events
   * @returns IEventsSearch
   */
  private buildSearchCriteria(): IEventsSearch {
    return {
      searchType: this.searchOption,
      schoolYear: this.searchYear,
      quarter: this.searchQuarter,
      eventName: this.searchFormGroup.get('eventName')?.value,
      minPoints: this.searchFormGroup.get('minPoints')?.value,
      clubName: this.searchFormGroup.get('clubName')?.value,
      endDate: dateFormat(this.searchFormGroup.get('endDate')?.value, environment.dateFormat)
    };
  }

  /**
   * Builds the search criteria for the joined events
   * @returns IEventsJoindSearch
   */
  private buildJoinedSearchCriteria(): IEventsJoinedSearch {
    return {
      sid: LoginComponent.currentUser.sid,
      searchType: EVENTS_SELECT_OPTIONS.ALL,
      schoolYear: this.searchYear,
      quarter: this.searchQuarter
    };
  }

  /**
   * Builds the search criteria for the owned events
   * @returns IEventsSearch
   */
  public buildOwnedSearchCriteria(): IEventsSearch {
    return {
      searchType: EVENTS_SELECT_OPTIONS.CLUB_LEADER_ID,
      schoolYear: this.searchYear,
      quarter: this.searchQuarter,
      clubLeaderId: LoginComponent.currentUser.sid
    };
  }

  get eventName(): FormControl {
    return this.searchFormGroup.get('eventName') as FormControl;
  }
  
  get minPoints(): FormControl {
    return this.searchFormGroup.get('minPoints') as FormControl;
  }

  get clubName(): FormControl {
    return this.searchFormGroup.get('clubName') as FormControl;
  }

  get endDate(): FormControl {
    return this.searchFormGroup.get('endDate') as FormControl;
  }

  /**
   * Gets the event search
   */
  public async onSubmit(): Promise<void> {
    this.searchFormGroup.markAsTouched({
      onlySelf: true
    });

    if (this.searchFormGroup.valid) {
      let message: any = await lastValueFrom(this.eventsService.getEventsSearch(this.buildSearchCriteria()));
      if (!message.status.success) {
        this.snackBar.open('An unknown error occurred.', 'Close', snackbarConfig);
      }
      else {
        this.eventsInfoListData = message.body;
        for (let data of this.eventsInfoListData) {
          data.startDate = new Date(data.startDate);
          data.endDate = new Date(data.endDate);
        }
        this.eventsInfoList = new MatTableDataSource(this.eventsInfoListData);
        this.eventsInfoList.paginator = this.paginator;
        this.eventsInfoList.sort = this.sort;
        this.clearFormFields();
      }
    }
    
    this.getJoinedEvents();
    this.getOwnedEvents();
    if (this.toggleJoinedEvent != null) {
      this.onToggleJoined(this.toggleJoinedEvent);
    }
    if (this.toggleOwnedEvent != null) {
      this.onToggleOwned(this.toggleOwnedEvent);
    }
  }

  /**
   * To change filtering for if the user has joined particular events
   * @param event MatButtonToggleChange
   */
  public onToggleJoined(event: MatButtonToggleChange): void {
    this.toggleOwnedValue = 'all';
    this.toggleJoinedEvent = event;
    if (event.value == 'all') {
      this.eventsInfoList = new MatTableDataSource(this.eventsInfoListData);
    }
    else if (event.value == 'joined') {
      let filteredEventsInfoListData: IEventsBaseInfo[] = [];
      for (let data of this.eventsInfoListData) {
        if (this.joinedEventsIds.includes(data.eid)) {
          filteredEventsInfoListData.push(data);
        }
      }
      this.eventsInfoList = new MatTableDataSource(filteredEventsInfoListData);
    }
    else {
      let filteredEventsInfoListData: IEventsBaseInfo[] = [];
      for (let data of this.eventsInfoListData) {
        if (!this.joinedEventsIds.includes(data.eid)) {
          filteredEventsInfoListData.push(data);
        }
      }
      this.eventsInfoList = new MatTableDataSource(filteredEventsInfoListData);
    }
  }

  /**
   * To Change filtering for if the student owns particular events
   * @param event MatButtonToggleChange
   */
  public onToggleOwned(event: MatButtonToggleChange): void {
    this.toggleJoinedValue = 'all';
    this.toggleOwnedEvent = event;
    if (event.value == 'all') {
      this.eventsInfoList = new MatTableDataSource(this.eventsInfoListData);
    }
    else if (event.value == 'owned') {
      let filteredEventsInfoListData: IEventsBaseInfo[] = [];
      for (let data of this.eventsInfoListData) {
        if (this.ownedEventsIds.includes(data.eid)) {
          filteredEventsInfoListData.push(data);
        }
      }
      this.eventsInfoList = new MatTableDataSource(filteredEventsInfoListData);
    }
    else {
      let filteredEventsInfoListData: IEventsBaseInfo[] = [];
      for (let data of this.eventsInfoListData) {
        if (!this.ownedEventsIds.includes(data.eid)) {
          filteredEventsInfoListData.push(data);
        }
      }
      this.eventsInfoList = new MatTableDataSource(filteredEventsInfoListData);
    }
  }

  /**
   * Gets the joind events
   */
  private async getJoinedEvents(): Promise<void> {
    let message: any = await lastValueFrom(this.eventsService.getJoinedEventsSearch(this.buildJoinedSearchCriteria()));
    if (!message.status.success) {
      this.snackBar.open('An unknown error occurred.', 'Close', snackbarConfig);
    }
    else {
      let joinedEvents = message.body;
      this.joinedEventsIds = [];
      for (let data of joinedEvents) {
        this.joinedEventsIds.push(data.eid);
      }
    }
  }

  /**
   * Gets the owned events
   */
  private async getOwnedEvents(): Promise<void> {
    if (LoginComponent.currentUser.accessLevel != 'high') {
      let message: any = await lastValueFrom(this.eventsService.getEventsSearch(this.buildOwnedSearchCriteria()));
      if (!message.status.success) {
        this.snackBar.open('An unknown error occurred.', 'Close', snackbarConfig);
      }
      else {
        let ownedEvents = message.body;
        this.ownedEventsIds = [];
        for (let data of ownedEvents) {
          this.ownedEventsIds.push(data.eid);
        }
      }
    }
    else {
      for (let data of this.eventsInfoListData) {
        this.ownedEventsIds.push(data.eid);
      }
    }
  }

  /**
   * Shortens the input data, if it's a string
   * @param data any
   * @returns the shortened version of the data
   */
  public shorten(data: any) {
    if (typeof data == 'string') {
      let short: string = data.substring(0, Math.round(window.innerWidth * 0.01));
      return short + (short.length < data.length ? '...' : '');
    }
    else {
      return data;
    }
  }

  /**
   * Opens the ConfirmComponent to confirm if the student wants to join the event
   * @param eid number
   */
  public joinEvent(eid: number) {
    if (!this.joinedEventsIds.includes(eid)) {
      this.dialog.open(ConfirmComponent, {
        data: {
          type: 'Join Event',
          message: '<strong>*</strong> You will get the minimum number of points when you join this event.<br><strong>*</strong> Your club leader will decide the number of points you get when you win.<br><br>Are you sure you want to join this event?',
          eid: eid,
          schoolYear: this.searchYear,
          quarter: this.searchQuarter
        }
      });
    }
  }

  /**
   * Opens the ConfirmComponent to confirm if the student wants to leave the event
   * @param eid number
   */
  public leaveEvent(eid: number): void {
    if (this.joinedEventsIds.includes(eid)) {
      this.dialog.open(ConfirmComponent, {
        data: {
          type: 'Leave Event',
          message: '<strong>*</strong> You will <strong>lose</strong> the number of points you earned if you leave this event<br><br>Are you sure you want to leave this event?',
          eid: eid,
          schoolYear: this.searchYear,
          quarter: this.searchQuarter
        }
      });
    }
  }

  /**
   * Opens the EventUpdateComponent which prompts the user to insert the fields to update the event
   * @param eid number
   */
  public updateEvent(eid: number): void {
    if (this.ownedEventsIds.includes(eid)) {
      for (let event of this.eventsInfoListData) {
        if (event.eid == eid) {
          this.dialog.open(EventUpdateComponent, {
            data: {
              event: event,
            }
          });
        }
      }
    }
  }

  /**
   * Opens the ConfirmComponent that confirms if the user wants to delete the event
   * @param eid number
   */
  public deleteEvent(eid: number): void {
    if (this.ownedEventsIds.includes(eid)) {
      this.dialog.open(ConfirmComponent, {
        data: {
          type: 'Delete Event',
          message: 'Are you sure you want to delete this event?',
          eid: eid
        }
      });
    }
  }

  /**
   * Opens the ConfirmComponent that confirms if the user wants to view the event
   * @param eid number
   */
  public viewEvent(eid: number) {
    for (let event of this.eventsInfoListData) {
      if (event.eid == eid) {
        this.dialog.open(EventDetailsComponent, {
          data: {
            event: event,
            join: !this.joinedEventsIds.includes(eid) && !this.ownedEventsIds.includes(eid),
            leave: this.joinedEventsIds.includes(eid),
            edit: this.ownedEventsIds.includes(eid)
          }
        });
      }
    }
  }

  /**
   * Applies a filter to the event table
   * @param event Event
   */
  public applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.eventsInfoList.filter = filterValue.trim().toLowerCase();

    if (this.eventsInfoList.paginator) {
      this.eventsInfoList.paginator.firstPage();
    }
  }

  /**
   * Checks if the item passed in is a Date
   * @param elem any
   * @returns if the elem is a Date
   */
  public isDate(elem: any): boolean {
    return elem instanceof Date;
  }
  
  /**
   * Clears the form fields
   */
  public clearFormFields(): void {
    this.searchFormGroup = this.fb.group({
      eventName: [''],
      minPoints: [''],
      clubName: [''],
      endDate: ['']
    });
    this.searchFormGroup.markAsUntouched({
      onlySelf: true
    });
  }
}

import { Component, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { lastValueFrom } from 'rxjs';
import { CLUBS_SELECT_OPTIONS, IClubsBaseInfo, IClubsSearch } from 'src/app/interfaces/clubs/clubs-interface';
import { ClubsService } from 'src/app/services/clubs/clubs.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import snackbarConfig from 'src/app/configs/snackbar-config';
import { MatDialog } from '@angular/material/dialog';
import { EventAddComponent } from 'src/app/components/events/add/event-add/event-add.component';
import { ClubDetailsComponent } from '../../details/club-details/club-details.component';
import { ILoginBaseInfo as ILoginBaseInfo } from 'src/app/interfaces/login/login-interface';
import { LoginComponent } from 'src/app/components/login/login.component';

/**
 * Clubs Component
 * 
 * @author Javier Huang
 */
@Component({
  selector: 'app-clubs',
  templateUrl: './clubs.component.html',
  styleUrls: ['./clubs.component.scss']
})
export class ClubsComponent {
  public SEARCH_OPTIONS = [
    { description: 'All', value: CLUBS_SELECT_OPTIONS.ALL },
    { description: 'Club Name', value: CLUBS_SELECT_OPTIONS.CLUB_NAME },
    { description: 'Description', value: CLUBS_SELECT_OPTIONS.DESCRIPTION },
    { description: 'Leader Name', value: CLUBS_SELECT_OPTIONS.LEADER_NAME }
  ];
  public COLUMN_NAMES: string[] = [ 'Club Name', 'Description', 'Name', 'Email', 'Actions', 'Owned' ];
  public COLUMN_DEF: string[] = [ 'clubName', 'description', 'leaderName', 'email', 'actions', 'owned' ];
  public COLUMNS = [
    { def: this.COLUMN_DEF[0], name: this.COLUMN_NAMES[0] },
    { def: this.COLUMN_DEF[1], name: this.COLUMN_NAMES[1] },
    { def: this.COLUMN_DEF[2], name: this.COLUMN_NAMES[2] },
    { def: this.COLUMN_DEF[3], name: this.COLUMN_NAMES[3] },
    { def: this.COLUMN_DEF[4], name: this.COLUMN_NAMES[4] },
    { def: this.COLUMN_DEF[5], name: this.COLUMN_NAMES[5] }
  ];
  public clubsInfoList!: MatTableDataSource<IClubsBaseInfo>;
  public clubsInfoListData: IClubsBaseInfo[] = [];
  public ownedClubsIds: number[] = [];

  public currentUser: ILoginBaseInfo = LoginComponent.currentUser;

  @ViewChild(MatPaginator) private paginator!: MatPaginator;
  @ViewChild(MatSort) private sort!: MatSort;

  public searchOption: string = this.SEARCH_OPTIONS[0].value;

  public searchFormGroup: FormGroup = this.fb.group({
    clubName: [''],
    description: [''],
    firstName: [''],
    lastName: ['']
  });

  constructor (
    public fb: FormBuilder,
    private clubsService: ClubsService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {
    this.onSubmit();
  }

  get clubName(): FormControl {
    return this.searchFormGroup.get('clubName') as FormControl;
  }
  
  get description(): FormControl {
    return this.searchFormGroup.get('description') as FormControl;
  }

  get firstName(): FormControl {
    return this.searchFormGroup.get('firstName') as FormControl;
  }

  get lastName(): FormControl {
    return this.searchFormGroup.get('lastName') as FormControl;
  }

  /**
   * Builds the search criteria for the clubs
   * @returns IClubsSearch
   */
    private buildSearchCriteria(): IClubsSearch {
    return {
      searchType: this.searchOption,
      clubName: this.searchFormGroup.get('clubName')?.value,
      description: this.searchFormGroup.get('description')?.value,
      firstName: this.searchFormGroup.get('firstName')?.value,
      lastName: this.searchFormGroup.get('lastName')?.value
    };
  }

  /**
   * Builds the search criteria for the clubs the user owns
   * @returns IClubsSearch
   */
  private buildClubsCriteria(): IClubsSearch {
    return {
      searchType: CLUBS_SELECT_OPTIONS.LEADER_ID,
      clubName: null,
      description: null,
      firstName: null,
      lastName: null,
      leaderId: LoginComponent.currentUser.sid
    };
  }

  /**
   * Gets the clubs that the user owns
   */
  public async getOwnedClubs(): Promise<void> {
    let message: any = await lastValueFrom(this.clubsService.getClubsSearch(this.buildClubsCriteria()));
    if (!message.status.success) {
      this.snackBar.open('An unknown error occurred.', 'Close', snackbarConfig);
    }
    else {
      for (let club of message.body) {
        this.ownedClubsIds.push(club.cid);
      }
    }
  }

  /**
   * Gets the search result of the user
   */
  public async onSubmit(): Promise<void> {
    this.searchFormGroup.markAsTouched({
      onlySelf: true
    });

    if (this.searchFormGroup.valid) {
      let message: any = await lastValueFrom(this.clubsService.getClubsSearch(this.buildSearchCriteria()));
      if (!message.status.success) {
        this.snackBar.open('An unknown error occurred.', 'Close', snackbarConfig);
      }
      else {
        this.clubsInfoListData = message.body;
        for (let data of this.clubsInfoListData) {
          data.leaderName = `${data.firstName} ${data.lastName}`;
        }
        this.clubsInfoList = new MatTableDataSource(this.clubsInfoListData);
        this.clubsInfoList.paginator = this.paginator;
        this.clubsInfoList.sort = this.sort;
        this.clearFormFields();
      }
    }

    this.getOwnedClubs();
  }

  /**
   * Opens the EventAddComponent dialog
   * @param cid number
   */
  public addEvent(cid: number) {
    if (this.currentUser.accessLevel == 'high' || (this.currentUser.accessLevel == 'medium' && this.ownedClubsIds.includes(cid))) {
      this.dialog.open(EventAddComponent, {
        data: {
          cid: cid
        }
      });
    }
  }
  
  /**
   * Opens the ClubDetailsComponent dialog
   * @param cid number
   */
  public viewClub(cid: number) {
    for (let club of this.clubsInfoListData) {
      if (club.cid == cid) {
        this.dialog.open(ClubDetailsComponent, {
          data: {
            club: club,
            add: this.currentUser.accessLevel == 'high' || (this.currentUser.accessLevel == 'medium' && this.ownedClubsIds.includes(cid))
          }
        });
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
      let short: string = data.substring(0, Math.round(window.innerWidth * 0.04));
      return short + (short.length < data.length ? '...' : '');
    }
    else {
      return data;
    }
  }

  /**
   * Applies the filter to the events table
   * @param event Event
   */
  public applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.clubsInfoList.filter = filterValue.trim().toLowerCase();

    if (this.clubsInfoList.paginator) {
      this.clubsInfoList.paginator.firstPage();
    }
  }

  /**
   * Clears the form fields
   */
  public clearFormFields(): void {
    this.searchFormGroup = this.fb.group({
      clubName: [''],
      description: [''],
      firstName: [''],
      lastName: ['']
    });
    this.searchFormGroup.markAsUntouched({
      onlySelf: true
    });
  }
}

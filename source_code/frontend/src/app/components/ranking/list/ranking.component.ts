import { Component, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { lastValueFrom } from 'rxjs';
import snackbarConfig from 'src/app/configs/snackbar-config';
import { ILoginBaseInfo } from 'src/app/interfaces/login/login-interface';
import { RANKING_SELECT_OPTIONS, IRankingBaseInfo, IRankingSearch } from 'src/app/interfaces/ranking/ranking-interface';
import { RankingService } from 'src/app/services/ranking/ranking.service';
import { LoginComponent } from '../../login/login.component';
import { PrizesService } from 'src/app/services/prizes/prizes.service';
import { MatDialog } from '@angular/material/dialog';
import { PrizesComponent } from '../../prizes/list/prizes/prizes.component';

import * as pdfMake from "pdfmake/build/pdfmake";
import * as pdfFonts from "pdfmake/build/vfs_fonts";
import { TDocumentDefinitions } from 'pdfmake/interfaces';


/**
 * Ranking Component
 * 
 * @author Javier Huang
 */
@Component({
  selector: 'app-ranking',
  templateUrl: './ranking.component.html',
  styleUrls: ['./ranking.component.scss']
})
export class RankingComponent {
  public currentUser: ILoginBaseInfo = LoginComponent.currentUser;
  public SEARCH_OPTIONS = [
    { description: 'All', value: RANKING_SELECT_OPTIONS.ALL },
    { description: 'Name', value: RANKING_SELECT_OPTIONS.NAME },
    { description: 'Grade', value: RANKING_SELECT_OPTIONS.GRADE },
    { description: 'Points Earned', value: RANKING_SELECT_OPTIONS.POINTS_EARNED }
  ];
  public COLUMN_NAMES: string[] = [ 'Name', 'Grade', 'Points Earned', 'Earned Prize', 'Prize Description', 'Top Winner', 'Random Winner' ];
  public COLUMN_DEF: string[] = [ 'name', 'grade', 'pointsEarned', 'prizeName', 'prizeDescription', 'isWinner', 'isRandom' ];
  public COLUMNS = [
    { def: this.COLUMN_DEF[0], name: this.COLUMN_NAMES[0] },
    { def: this.COLUMN_DEF[1], name: this.COLUMN_NAMES[1] },
    { def: this.COLUMN_DEF[2], name: this.COLUMN_NAMES[2] },
    { def: this.COLUMN_DEF[3], name: this.COLUMN_NAMES[3] },
    { def: this.COLUMN_DEF[4], name: this.COLUMN_NAMES[4] },
    { def: this.COLUMN_DEF[5], name: this.COLUMN_NAMES[5] },
    { def: this.COLUMN_DEF[6], name: this.COLUMN_NAMES[6] }
  ];
  public rankingInfoList!: MatTableDataSource<IRankingBaseInfo>;
  public rankingInfoListData: IRankingBaseInfo[] = [];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  public years: string[] = ['2021-2022', '2022-2023'];
  public quarters: number[] = [1, 2, 3, 4];

  public searchYear: string = this.years[1];
  public searchQuarter: number = this.quarters[2];
  public searchOption: string = this.SEARCH_OPTIONS[0].value;

  public searchFormGroup: FormGroup = this.fb.group({
    firstName: [''],
    lastName: [''],
    grade: [''],
    pointsEarned: ['']
  });

  constructor (
    public fb: FormBuilder,
    private rankingService: RankingService,
    private prizeService: PrizesService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {
    this.onSubmit();
  }

  /**
   * Builds the search criteria for rankings
   * @returns IRankingSearch
   */
  private buildSearchCriteria(): IRankingSearch {
    return {
      searchType: this.searchOption,
      schoolYear: this.searchYear,
      quarter: this.searchQuarter,
      firstName: this.searchFormGroup.get('firstName')?.value,
      lastName: this.searchFormGroup.get('lastName')?.value,
      grade: this.searchFormGroup.get('grade')?.value,
      pointsEarned: this.searchFormGroup.get('pointsEarned')?.value
    };
  }

  get firstName(): FormControl {
    return this.searchFormGroup.get('firstName') as FormControl;
  }
  
  get lastName(): FormControl {
    return this.searchFormGroup.get('lastName') as FormControl;
  }

  get grade(): FormControl {
    return this.searchFormGroup.get('grade') as FormControl;
  }

  get pointsEarned(): FormControl {
    return this.searchFormGroup.get('pointsEarned') as FormControl;
  }

  /**
   * Gets the rankings
   */
  public async onSubmit(): Promise<void> {
    this.searchFormGroup.markAsTouched({
      onlySelf: true
    });

    if (this.searchFormGroup.valid) {
      let message: any = await lastValueFrom(this.rankingService.getRankingSearch(this.buildSearchCriteria()));
      if (!message.status.success) {
        this.snackBar.open('An unknown error occurred.', 'Close', snackbarConfig);
      }
      else {
        this.rankingInfoListData = message.body;
        for (let data of this.rankingInfoListData) {
          data.name = `${data.firstName} ${data.lastName}`;
        }
        this.rankingInfoList = new MatTableDataSource(this.rankingInfoListData);
        this.rankingInfoList.paginator = this.paginator;
        this.rankingInfoList.sort = this.sort;
        this.clearFormFields();
      }
    }
  }

  /**
   * Grants the prizes
   */
  public async grantPrizes(): Promise<void> {
    if (LoginComponent.currentUser.accessLevel == 'high') {
      let message: any = await lastValueFrom(this.rankingService.grantPrizes({ schoolYear: this.searchYear, quarter: this.searchQuarter }));
      if (!message.status.success) {
        this.snackBar.open('An unknown error occurred.', 'Close', snackbarConfig);
      }
      else {
        this.snackBar.open('You have successfully granted prizes to everyone!', 'Close', snackbarConfig);
      }
      setTimeout(() => {
        location.reload();
      }, snackbarConfig.reloadDuration);
    }
  }

  /**
   * Shows the prizes
   */
  public async showPrizes(): Promise<void> {
    let message: any = await lastValueFrom(this.prizeService.getPrizesSearch({ schoolYear: this.searchYear, quarter: this.searchQuarter }));
    if (!message.status.success) {
      this.snackBar.open('An unknown error occurred.', 'Close', snackbarConfig);
    }
    else {
      this.dialog.open(PrizesComponent, {
        width: '70vw',
        data: {
          prizes: message.body,
          schoolYear: this.searchYear,
          quarter: this.searchQuarter
        }
      });
    }
  }

  /**
   * Applies the filter to the rankings table
   * @param event Event
   */
  public applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.rankingInfoList.filter = filterValue.trim().toLowerCase();

    if (this.rankingInfoList.paginator) {
      this.rankingInfoList.paginator.firstPage();
    }
  }
  
  /**
   * Shortens the input data, if it's a string
   * @param data any
   * @returns the shortened version of the data
   */
  public shorten(data: any) {
    if (typeof data == 'string') {
      let short: string = data.substring(0, Math.round(window.innerWidth * 0.03));
      return short + (short.length < data.length ? '...' : '');
    }
    else {
      return data;
    }
  }

  /**
   * Checks if the element is a boolean
   * @param elem any
   * @returns if the element is a boolean
   */
  public isBoolean(elem: any): boolean {
    return typeof elem == 'boolean';
  }

  /**
   * Clears the form fields
   */
  public clearFormFields(): void {
    this.searchFormGroup = this.fb.group({
      firstName: [''],
      lastName: [''],
      grade: [''],
      pointsEarned: ['']
    });
    this.searchFormGroup.markAsUntouched({
      onlySelf: true
    });
  }

  /**
   * Builds the report criteria
   * @param grade number
   * @returns IRankingSearch
   */
  private buildReportCriteria(grade: number): IRankingSearch {
    return {
      searchType: RANKING_SELECT_OPTIONS.GRADE,
      schoolYear: this.searchYear,
      quarter: this.searchQuarter,
      grade: grade
    };
  }

  /**
   * Returns all the rankings given a grade
   * @param grade number
   * @returns IRankingBaseInfo[]
   */
  public async getAllRankings(grade: number): Promise<IRankingBaseInfo[]> {
    let message: any = await lastValueFrom(this.rankingService.getRankingSearch(this.buildReportCriteria(grade)));
    if (!message.status.success) {
      this.snackBar.open('An unknown error occurred.', 'Close', snackbarConfig);
    }
    else {
      return message.body;
    }
    return [];
  }

  /**
   * @desc pdf content
   * @param {Array} sections
   * @param {Number} pageWidth Width in inches
   * @param {Number} pageHeight Width in inches
   * @return title, pageSize, content, pageMargin
   * */
  public pdfContent(sections: any, pageWidth: number, pageHeight: number) {
    let pageSize = {
      width: pageWidth * 72,
      height: pageHeight * 72
    };
    let imageWidth = 1.0;
    let imagePercentage = 20;
    let pageMargins = [40, 60, 40, 60];
    let content = [];
    sections.forEach((section: any, si: any) => {
        content.push({
          text: section.heading || `Section ${si + 1}`,
          fontSize: 20,
          alignment: 'center',
          margin: [15, 15],
          // If it is the first section, do not insert a pageBreak
          pageBreak: si === 0 ? null : 'before'
        });
        section.images.forEach((image: any, j: any) => {
          content.push({
            image,
            alignment: 'center',
            width: (pageSize.width * imageWidth) * imagePercentage / 100,
            pageBreak: j !== 0 ? 'before' : null
          });
        });
        if (section.rows != undefined && section.rows.length > 0) {
          let tableBody = [];
          section.rows.forEach((row: any[]) => {
            tableBody.push(row);
          });
          content.push({
            layout: 'lightHorizontalLines',
            table: {
              headerRows: 1,
              widths: [ 'auto', 'auto', 'auto', 'auto', 'auto', 'auto', 'auto', 'auto' ],
              body: tableBody
            }
          });
        }
    });
    return {
      pageSize,
      content,
      pageMargins
    }
  };

  /**
   * @desc Open pdf
   * @param {Array} sections
   * @param {Number} pageWidth Width in inches
   * @param {Number} pageHeight Width in inches
   * */
  public openPdf(sections: any, pageWidth: number, pageHeight: number) {
    let {pageSize, content, pageMargins} = this.pdfContent(sections, pageWidth, pageHeight);
    let docDefinition = {
      info: {  title: 'RamsEvents Rankings Report' },
      pageSize,
      content: content,
      pageMargins
    } as TDocumentDefinitions;

    pdfMake.createPdf(docDefinition).open();
  };

  /**
   * Generates the report
   */
  public async generateReport(): Promise<void> {
    let sections = [
      {
        heading: `RamsEvents Rankings Report For ${this.searchYear}, Quarter ${this.searchQuarter}`,
        images: [
          // Logo in base64
          'data:image/png;base64,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'
        ],
        rows: []
      },
    ];
    for (let grade = 9; grade <= 12; grade++) {
      let gradeRankings: IRankingBaseInfo[] = await this.getAllRankings(grade);
      let rawArray: any[][] = [[ 'First Name', 'Last Name', 'Grade', 'Points Earned', 'Top Winner', 'Random Winner', 'Earned Prize', 'Prize Description' ]];
      for (let ranking of gradeRankings) {
        let array: any[] = [];
        for (let property in ranking) {
          if (property != 'sid' && property != 'studentId' && property != 'schoolYear' && property != 'quarter')
          array.push(ranking[property].toString());
        }
        rawArray.push(array);
      }
      sections.push({
        heading: `Grade ${grade} Rankings`,
        images: [],
        rows: rawArray
      });
    }
    this.openPdf(sections, 12, 8);
  }
}

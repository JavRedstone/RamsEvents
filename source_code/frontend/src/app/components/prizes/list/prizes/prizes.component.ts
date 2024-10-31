import { Component, Inject} from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

/**
 * Prizes Component
 * 
 * @author Javier Huang
 */
@Component({
  selector: 'app-prizes',
  templateUrl: './prizes.component.html',
  styleUrls: ['./prizes.component.scss']
})
export class PrizesComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {

  }
}

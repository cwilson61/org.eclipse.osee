import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { share} from 'rxjs/operators';
import { PlConfigActionService } from '../../services/pl-config-action.service';
import { actionableItem, PLConfigCreateAction, targetedVersion } from '../../types/pl-config-actions';
import { user } from '../../types/pl-config-users';

@Component({
  selector: 'app-create-action-dialog',
  templateUrl: './create-action-dialog.component.html',
  styleUrls: ['./create-action-dialog.component.sass']
})
export class CreateActionDialogComponent implements OnInit {
  users:Observable<user[]> = this.actionService.users;
  arb: Observable<actionableItem[]> = this.actionService.ARB.pipe(share());
  targetedVersions!: Observable<targetedVersion[]>;
  constructor(public dialogRef: MatDialogRef<CreateActionDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: PLConfigCreateAction, public actionService: PlConfigActionService) { 
  }

  ngOnInit(): void {
  }
  onNoClick(): void{
    this.dialogRef.close();
  }
  selectActionableItem() {
    this.targetedVersions = this.actionService.getVersions(this.data.actionableItem.id);
  }

}

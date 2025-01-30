import {Component, OnDestroy, OnInit} from '@angular/core';
import {DriveDto} from '../../dtos/drive-dto';
import {AxiosService} from '../../services/axios.service';
import {AuthService} from '../../services/auth.service';
import {DriveProviderService} from '../../drive-provider.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-dashboard-screen',
  imports: [],
  templateUrl: './dashboard-screen.component.html',
  styleUrl: './dashboard-screen.component.css'
})
export class DashboardScreenComponent implements OnInit, OnDestroy {
  private drive_sub?: Subscription;
  protected drives: DriveDto[] = [];

  constructor(
    private readonly axiosService: AxiosService,
    private readonly authService: AuthService,
    private readonly driveProvider: DriveProviderService,
  ) {
  }

  ngOnInit() {
    this.fetchDrives();
    this.drive_sub = this.driveProvider.drives.subscribe(drives => this.drives = drives);
  }

  ngOnDestroy() {
    if (this.drive_sub)
      this.drive_sub.unsubscribe()
  }

  fetchDrives() {
    this.axiosService.request(
      "GET",
      "/drives",
      ""
    ).then(response => {
      return response.data;
    }).then(drives => {
      if (drives == null)
        throw Error("drives is null")
      this.drives = drives;
    }).catch(error => {
      if (error.response.status === 401)
        this.authService.deleteJwtToken();
    });
  }

  addDrive() {
    let date = new Date();
    let drive: DriveDto = new DriveDto(
      (date.getFullYear().toString() + date.getMonth().toString() + date.getDate().toString())
    )
    this.axiosService.request(
      "POST",
      "/drives",
      drive
    ).then(response => {
      return response.data;
    }).then(drive => {
      this.driveProvider.addDrive(drive)
    }).catch(error => {
      if (error.response.status === 401)
        this.authService.deleteJwtToken();
    });
  }

  protected readonly Date = Date;
}

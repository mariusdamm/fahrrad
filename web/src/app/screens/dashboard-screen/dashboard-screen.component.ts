import {AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {DriveDto} from '../../dtos/drive-dto';
import {AxiosService} from '../../services/axios.service';
import {AuthService} from '../../services/auth.service';
import {DriveProviderService} from '../../drive-provider.service';
import {Subscription} from 'rxjs';

declare let bootstrap: any;

@Component({
  selector: 'app-dashboard-screen',
  imports: [],
  templateUrl: './dashboard-screen.component.html',
  styleUrl: './dashboard-screen.component.css'
})
export class DashboardScreenComponent implements OnInit, OnDestroy, AfterViewInit {
  private drive_sub?: Subscription;
  protected drives: DriveDto[] = [];
  @ViewChild('driveDateInput') dateInput!: ElementRef<HTMLInputElement>;

  constructor(
    private readonly axiosService: AxiosService,
    private readonly authService: AuthService,
    private readonly driveProvider: DriveProviderService,
  ) {
  }

  ngOnInit() {
    this.drive_sub = this.driveProvider.drives
      .subscribe(drives => this.drives = drives);
    this.fetchDrives();
  }

  ngOnDestroy() {
    if (this.drive_sub)
      this.drive_sub.unsubscribe()
  }

  ngAfterViewInit() {
    let date = new Date()
    this.dateInput.nativeElement.value = date.toISOString().split('T')[0];
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
      this.driveProvider.addDrives(drives);
    }).catch(error => {
      if (error.response.status === 401)
        this.authService.deleteJwtToken();
    });
  }

  addDrive() {
    let drive: DriveDto = new DriveDto(
      this.dateInput.nativeElement.value.toString()
    )
    this.axiosService.request(
      "POST",
      "/drives",
      drive
    ).then(response => {
      return response.data;
    }).then(drive => {
      this.driveProvider.addDrive(drive)
      const bsCollapse = new bootstrap.Collapse('#postSuccess', {});
      bsCollapse.show();
      setTimeout(() => bsCollapse.hide(), 3000);
    }).catch(error => {
      if (error.response.status === 401)
        this.authService.deleteJwtToken();

      const bsCollapse = new bootstrap.Collapse(error.response.status == 302 ? '#postWarning' : '#postError', {});
      bsCollapse.show();
      setTimeout(() => bsCollapse.hide(), 3000);
    });
  }
}

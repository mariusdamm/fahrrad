import {Component, OnInit} from '@angular/core';
import {DriveDto} from '../../dtos/drive-dto';
import {AxiosService} from '../../services/axios.service';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-dashboard-screen',
  imports: [],
  templateUrl: './dashboard-screen.component.html',
  styleUrl: './dashboard-screen.component.css'
})
export class DashboardScreenComponent implements OnInit {
  drives: DriveDto[] = [];

  constructor(
    private readonly axiosService: AxiosService,
    private readonly authService: AuthService,
  ) {
  }

  ngOnInit() {
    this.fetchDrives();
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
    let drive: DriveDto = new DriveDto(date)
    this.axiosService.request(
      "POST",
      "/drives",
      drive
    ).then(response => {
      console.log(response);
    })
  }

  protected readonly Date = Date;
}

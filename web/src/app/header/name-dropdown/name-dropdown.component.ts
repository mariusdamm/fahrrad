import {Component, OnInit} from '@angular/core';
import {AppuserDto} from '../../dtos/appuser-dto';
import {AxiosService} from '../../services/axios.service';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-name-dropdown',
  imports: [],
  templateUrl: './name-dropdown.component.html',
  styleUrl: './name-dropdown.component.css'
})
export class NameDropdownComponent implements OnInit {

  user?: AppuserDto;

  constructor(
    private readonly axiosService: AxiosService,
    private readonly authService: AuthService,
  ) {
  }

  ngOnInit() {
    this.fetchUser();
  }

  logoutUser() {
    this.authService.deleteJwtToken();
    window.location.href = '';
  }

  fetchUser() {
    this.axiosService.request(
      "GET",
      "/user/self",
      ''
    ).then(response => {
      return response.data;
    }).then(self => {
      if (self === null)
        throw new Error("Self is null");

      this.user = self;
    }).catch(error => {
      if (error.response.status === 401)
        this.logoutUser();
    });
  }
}

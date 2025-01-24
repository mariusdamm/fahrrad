import {Component} from '@angular/core';
import {UtilService} from "../../services/util.service";
import {AuthService} from "../../services/auth.service";
import {AxiosService} from "../../services/axios.service";
import {RegisterDto} from "../../dtos/register-dto";
import {FormsModule} from "@angular/forms";
import {Router} from "@angular/router";

declare let bootstrap: any;

@Component({
  selector: 'app-register-screen',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './register-screen.component.html',
  styleUrl: './register-screen.component.css'
})
export class RegisterScreenComponent {

  constructor(private readonly utilService: UtilService,
              private readonly jwtService: AuthService,
              private readonly axiosService: AxiosService,
              private readonly router: Router,
  ) {
  }

  registerUser(event: Event, nameInputId: string, usernameInputId: string, passwordInputId: string) {
    event.preventDefault();

    const usernameInput = document.getElementById(usernameInputId) as HTMLInputElement;
    const passwordInput = document.getElementById(passwordInputId) as HTMLInputElement;

    const username = usernameInput.value.trim();
    const password = passwordInput.value.trim();

    if (username === '') {
      this.utilService.highlightInvalidInput(usernameInput);
    }
    if (password === '') {
      this.utilService.highlightInvalidInput(passwordInput);
    }
    if (username === '' || password === '') {
      return;
    }

    let registerDto: RegisterDto = new RegisterDto(username, password);

    this.axiosService.requestWithoutToken(
      "POST",
      "/auth/register",
      registerDto
    ).then(response => {
      if (response.status > 299)
        throw new Error('Network response was not ok');
      return response.data;
    }).then(loginResponse => {
      this.jwtService.saveJwtToken(loginResponse.token);
      this.router.navigate(['/dashboard']).then(() => false);
    }).catch(error => {
      console.error('Fetch error:', error);
      const bsCollapse = new bootstrap.Collapse('#registerDangerCollapse', {});
      bsCollapse.show();
      setTimeout(() => bsCollapse.hide(), 3000);
    });
  }
}

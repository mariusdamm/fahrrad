import {Routes} from '@angular/router';
import {DashboardScreenComponent} from "./screens/dashboard-screen/dashboard-screen.component";
import {LoginScreenComponent} from "./screens/login-screen/login-screen.component";
import {RegisterScreenComponent} from "./screens/register-screen/register-screen.component";
import {AuthGuardService} from "./services/auth-guard.service";

export const publicRoutes: Routes = [
  {path: 'login', component: LoginScreenComponent, title: 'Login'},
  {path: 'register', component: RegisterScreenComponent, title: 'Register'},
]

export const privateRoutes: Routes = [
  {path: '', redirectTo: 'dashboard', pathMatch: 'full'},
  {path: 'dashboard', component: DashboardScreenComponent, title: 'Dashboard', canActivate: [AuthGuardService]},
]

export const routes: Routes = [...publicRoutes, ...privateRoutes];

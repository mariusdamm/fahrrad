import { Component } from '@angular/core';
import {DriveDto} from '../../dtos/drive-dto';

@Component({
  selector: 'app-dashboard-screen',
  imports: [],
  templateUrl: './dashboard-screen.component.html',
  styleUrl: './dashboard-screen.component.css'
})
export class DashboardScreenComponent {
  drives: DriveDto[] = [];
}

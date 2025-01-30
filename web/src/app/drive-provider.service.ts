import { Injectable } from '@angular/core';
import {DriveDto} from './dtos/drive-dto';
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DriveProviderService {

  private readonly _drives = new BehaviorSubject<DriveDto[]>([]);

  constructor() { }

  get drives(): Observable<DriveDto[]> {
    return this._drives.asObservable()
  }

  addDrive(drive: DriveDto) {
    this._drives.next([...this._drives.getValue(), drive]);
  }
}

export class DriveDto {
  id: number = 0;
  date: Date;

  constructor(date: Date) {
    this.date = date;
  }
}

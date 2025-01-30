export class DriveDto {
  id: number = 0;
  date: string;

  constructor(date: string) {
    this.date = date;
  }
}

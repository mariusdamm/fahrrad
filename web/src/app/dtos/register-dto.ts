export class RegisterDto {
  username: string = "";
  password: string = "";
  name: string = "";


  constructor(username: string, password: string) {
    this.username = username;
    this.password = password;
  }
}

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Response } from '../model/response';
import { User } from '../model/user';
import { TokenStorageService } from './token-storage.service';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AccountService {


  constructor(
    private http: HttpClient,
    private tokenStorageService:TokenStorageService
  ) { }



  register(user:User){
    return this.http.post<Response>("http://localhost:8081/api/user/register", user);
  }

  login(username:string, password:string){
   
    let formData =  new FormData();
    formData.append("username", username);
    formData.append("password", password);
    return this.http.post("http://localhost:8081/login", formData);
  }

  isLogin(){
    const token = this.tokenStorageService.getToken();
    if( token != null){
      const expiry = (JSON.parse(atob(token.split('.')[1]))).exp;
      const tokenExpired = Date.now() > (expiry * 1000);
      if (tokenExpired) return false;
      return true;      
    }

    return false;

  }
}

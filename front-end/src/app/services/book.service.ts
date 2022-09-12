import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserBook } from '../model/userBook';
import { TokenStorageService } from './token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  /**
   * @TODO
   * reuse the bloc of token and headers 
   */
  constructor(
    private http:HttpClient,
    private tokenStorageService:TokenStorageService
  ) { }

  findAllById(){
    const headers = this.constructHeader();
    return this.http.get("http://localhost:8080/home", {headers});
  }

  getBook(key:string){
    const headers = this.constructHeader();
    return this.http.get("http://localhost:8080/books/"+key, {headers});
  }

  addBook(userBook: UserBook){    
    const headers = this.constructHeader()       
    return this.http.post("http://localhost:8080/addUserBook", userBook, { headers });
  }

  constructHeader(){
    const token = this.tokenStorageService.getToken();    
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    return headers;
  }
  
}

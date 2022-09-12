import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(
    private http:HttpClient
  ) { }



  search(query:string){
    const params = new HttpParams().set('query', query);
    return this.http.get("http://localhost:8080/search", {params});

  }
}

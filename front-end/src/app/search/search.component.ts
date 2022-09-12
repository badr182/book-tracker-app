import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { Book } from '../model/book';
import { SearchService } from '../services/search.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  query:string= "";
  books:Book[] = [];
  constructor(
    private searchService:SearchService,
    private route:ActivatedRoute
  ) { }

  ngOnInit(): void {
    const q = this.route.snapshot.queryParamMap.get("query");
    if ( q !== null ) {
      this.searchService.search(q).subscribe((data:any) => {      
        this.books = data;        
      });
    }
  }

  onSubmit(){    
    this.searchService.search(this.query).subscribe((data:any) => {      
      this.books = data;
    });
  }

}

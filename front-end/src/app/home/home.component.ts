import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BookService } from '../services/book.service';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  query: string = "";
  isLogin: boolean = false;
  books:any;

  constructor(
    private router:Router,
    private bookService:  BookService,
    private accoutSerivce: AccountService
  ) { }

  ngOnInit(): void {
    this.bookService.findAllById().subscribe((data) => {
      this.books = data
    })
    this.isLogin = this.accoutSerivce.isLogin();
  }


  onSubmit(){    
    this.router.navigate(["search"], {queryParams: { query: this.query} });
  }

}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Book } from '../model/book';
import { UserBook } from '../model/userBook';
import { AccountService } from '../services/account.service';
import { BookService } from '../services/book.service';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {

  query:string = "";
  book:any = null;
  key: string = "";
  isLogin:boolean = false;

  userBook:UserBook = new UserBook(null,null,null,null,null);

  constructor(
    private route:ActivatedRoute,
    private bookService:BookService,
    private accountService:AccountService,
    private router:Router
  ) { }

  ngOnInit(): void {
    this.key = this.route.snapshot.params['key'];
    this.bookService.getBook(this.key).subscribe((data:any) => {   
      if( data != null){     
        this.book = data.book;
        if(data.userBooks != undefined ){
          this.userBook = data.userBooks;
          this.userBook.bookId =  data.userBooks.key.bookId;
        }
      }
    })
    this.isLogin = this.accountService.isLogin();
  }

  onSubmit(){
    this.router.navigate(["search"], {queryParams: { query: this.query} });
  }

  onSubmitUserBook(){
    this.userBook.rating = Number(this.userBook.rating);
    this.bookService.addBook(this.userBook).subscribe( (data) => {     
    }) ;
  }

}


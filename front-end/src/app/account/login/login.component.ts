import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { AccountService } from 'src/app/services/account.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(
    private accountService:AccountService,
    private tokenStorage:TokenStorageService,
    private router: Router    
  ) { }

  model:User = new User("","","");
  errorMessage:String = "";

  ngOnInit(): void {   
  }

  onSubmit(){    
    this.accountService.login(this.model.username, this.model.password)
    .subscribe(
      {
        next: (data:any) => {
          this.tokenStorage.saveToken(data.access_token);
          this.tokenStorage.saveUser(data.user);
          this.router.navigate([''])
        },
        error: (err) => { 
          if(err.status == "401") this.errorMessage= "Bad credentials"
        }
      }
    );
  }

}

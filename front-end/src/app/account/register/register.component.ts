import { Component, OnInit } from '@angular/core';
import { Register } from 'src/app/model/account';
import { Response } from 'src/app/model/response';
import { User } from 'src/app/model/user';
import { AccountService } from 'src/app/services/account.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(
    private accountService:AccountService
  ) { }

  model = new Register("","","");
  response:Response = new Response(null,null);

  ngOnInit(): void {
  }

  onSubmit(){    
    const user:User = new User("",this.model.username,this.model.password);
    this.accountService.register(user).subscribe( (data:Response) => {      
      this.response = data
    });
  }

}

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './account/login/login.component';
import { RegisterComponent } from './account/register/register.component';
import { BookComponent } from './book/book.component';
import { HomeComponent } from './home/home.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { SearchComponent } from './search/search.component';

const routes: Routes = [
  { path:"", component:HomeComponent },
  { path:"register", component:RegisterComponent },
  { path:"login", component:LoginComponent },
  { path:"search", component:SearchComponent },
  { path:"book/:key", component:BookComponent  },
  { path: "**", component:NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

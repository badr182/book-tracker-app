import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './account/register/register.component';
import { FormsModule } from '@angular/forms';
import { PasswordPatternDirective } from './directives/password-pattern.directive';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './account/login/login.component';
import { SearchComponent } from './search/search.component';
import { BookComponent } from './book/book.component';
import { NotFoundComponent } from './not-found/not-found.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    RegisterComponent,
    PasswordPatternDirective,
    LoginComponent,
    SearchComponent,
    BookComponent,
    NotFoundComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

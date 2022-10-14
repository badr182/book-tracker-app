package io.javabrains.userBooks;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

//import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import io.javabrains.book.Book;
import io.javabrains.book.BookRepository;
import io.javabrains.model.User;
import io.javabrains.user.BooksByUser;
import io.javabrains.user.BooksByUserRepository;
import io.javabrains.utils.AuthenticationService;

import com.datastax.oss.driver.api.core.uuid.*;
import org.apache.commons.lang3.StringUtils;

@RestController
public class UserBooksController {
	
	@Autowired
	AuthenticationService authenticaionService ;
	
	@Autowired
	UserBooksRepository userBooksRepository;
	
	@Autowired 
    BooksByUserRepository booksByUserRepository;

    @Autowired 
    BookRepository bookRepository;
	
	@PostMapping("addUserBook")
	public Map<String,String> addBookForUser( @RequestBody Map<String, Object> userBook, HttpServletRequest request) {
		Map<String,String> response = new HashMap<String, String>();

		User user = authenticaionService.getUser(request);
			
		String bookId = (String) userBook.get("bookId");
		Optional<Book> optionalBook = bookRepository.findById(bookId);
		
		if ( optionalBook.isPresent() && user != null ) {
			
			Book book = optionalBook.get();        			
			UserBooks userBooks = new UserBooks();
			UserBooksPrimaryKey key = new UserBooksPrimaryKey();

			key.setUserId(user.getId());
			key.setBookId(bookId);
			userBooks.setKey(key);
			
			Integer rating =   userBook.get("rating") != null ? (Integer) userBook.get("rating") : 0;
			LocalDate startedDate = userBook.get("startedDate") != null ? LocalDate.parse((String) userBook.get("startedDate")) : null ;
			LocalDate completedDate = userBook.get("completedDate") != null ? LocalDate.parse((String) userBook.get("completedDate")) : null ;			
			String readingStatus = userBook.get("readingStatus") != null ? (String) userBook.get("readingStatus") : null ;
			
			userBooks.setRating(rating);
			userBooks.setStartedDate(startedDate);
			userBooks.setCompletedDate(completedDate);
			userBooks.setReadingStatus(readingStatus);			
			userBooksRepository.save(userBooks);
			
//			Optional<BooksByUser> booksByUser1 = booksByUserRepository.findById(user.getId());
			BooksByUser booksByUser = new BooksByUser();
			booksByUser.setId(user.getId());
	        booksByUser.setBookId(bookId);
	        booksByUser.setBookName(book.getName());
	        booksByUser.setCoverIds(book.getCoverIds());
	        booksByUser.setAuthorNames(book.getAuthorNames());
	        booksByUser.setReadingStatus(readingStatus);
	        booksByUser.setRating(rating);        
	  
	        booksByUserRepository.save(booksByUser);
	        response.put("status", "success");
	        response.put("content", "user book was successfully updated");
	        
		}else {
		  response.put("status", "error");
	      response.put("content", "user book not updated successfully");
		}
		
        
        return response;
		
	} 

}

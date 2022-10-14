package io.javabrains.book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import io.javabrains.model.User;
import io.javabrains.userBooks.UserBooks;
import io.javabrains.userBooks.UserBooksPrimaryKey;
import io.javabrains.userBooks.UserBooksRepository;
import io.javabrains.utils.AuthenticationService;
import lombok.extern.slf4j.Slf4j;

@RestController
public class BookController {
	
	private final String COVER_IMAGE_ROOT = "http://covers.openlibrary.org/b/id/";
	
	@Autowired
	AuthenticationService authenticaionService;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	UserBooksRepository UserBooksRepository;
	
	@GetMapping(value="/books/{bookId}")
	public Map<String, Object> getBook( @PathVariable String bookId, HttpServletRequest request) {
		Optional<Book> optionalBook = bookRepository.findById(bookId);
		if( optionalBook.isPresent() ) {
			Map<String, Object> response = new HashMap<String, Object>();
			
			Book book = optionalBook.get();
			String coverImageUrl;
			if(book.getCoverIds() != null && book.getCoverIds().size() > 0 ) {
				coverImageUrl = COVER_IMAGE_ROOT + book.getCoverIds().get(0)+"-L.jpg";
				book.setCoverImageUrl(coverImageUrl);
			}else {
				book.setCoverImageUrl(null);				
			}
			
			User user = authenticaionService.getUser(request);
 
			if (user != null) {
				UserBooksPrimaryKey key = new UserBooksPrimaryKey();
				key.setBookId(bookId);
				key.setUserId(user.getId());
				Optional<UserBooks> userBooks = UserBooksRepository.findById(key);
				if( userBooks.isPresent() ) {
					response.put("userBooks", userBooks.get());
				}else {
					response.put("userBooks", new UserBooks());
				}
				
			}
			response.put("book", book);
			return response;
		}
		
		return null;
	}
}

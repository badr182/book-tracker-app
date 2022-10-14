package io.javabrains.home;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.datastax.oss.driver.shaded.guava.common.base.Function;
import com.datastax.oss.driver.shaded.guava.common.base.Predicate;

import io.javabrains.model.User;
import io.javabrains.user.BooksByUser;
import io.javabrains.user.BooksByUserRepository;
import io.javabrains.userBooks.UserBooksRepository;
import io.javabrains.utils.AuthenticationService;

@RestController
public class HomeController {
	
	private final String COVER_IMAGE_ROOT = "http://covers.openlibrary.org/b/id/";
	
	@Autowired
	AuthenticationService authenticaionService ;
	
	@Autowired
	BooksByUserRepository booksByUserRepository;
	
	@Autowired
	UserBooksRepository userBooksRepository;
	
	
	@GetMapping("/home")
	public List<BooksByUser> home(HttpServletRequest request) {
		
		User user = authenticaionService.getUser(request);
		
		if ( user != null ) {
			String userId = user.getId();
			Slice<BooksByUser>  booksSlice = booksByUserRepository.findAllById(userId, CassandraPageRequest.of(0, 100));
			List<BooksByUser> booksByUser = booksSlice.getContent();
			booksByUser = booksByUser
					.stream()
					//.distinct()
					.filter( distinctByKey( p -> p.getBookId()))
					.map(book ->{
						book.getCoverIds().get(0);
						String coverImageUrl = "/images/no-image.png";
						if(book.getCoverIds() != null & book.getCoverIds().size() > 0 ) {
							coverImageUrl = COVER_IMAGE_ROOT + book.getCoverIds().get(0)+"-M.jpg";
						}
						book.setCoverUrl(coverImageUrl);
						return book;
			}).collect(Collectors.toList());
			return booksByUser;
		}
		return null;
	}
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
	    final Set<Object> seen = new HashSet<>();
	    return t -> seen.add(keyExtractor.apply(t));
	}

}

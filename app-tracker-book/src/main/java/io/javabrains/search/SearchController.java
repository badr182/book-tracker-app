package io.javabrains.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.util.StringUtils;

import reactor.core.publisher.Mono;

@RestController
public class SearchController {
	
	private final String COVER_IMAGE_ROOT = "http://covers.openlibrary.org/b/id/";
	
	private final WebClient webClient;
	public SearchController(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.exchangeStrategies(ExchangeStrategies.builder()
				.codecs(configure -> configure
						.defaultCodecs()
						.maxInMemorySize(16 * 1024 * 1024)).build()) // 262144
				.baseUrl("http://openlibrary.org/search.json").build();
	}

	@GetMapping(value="/search")
	public List<SearchResultBook> getSearchResult(@RequestParam String query) {
		Mono<SearchResult> resultsMono =  this.webClient.get()
			.uri("?q={query}", query)
			.retrieve()
			.bodyToMono(SearchResult.class);
		SearchResult result = resultsMono.block();
		List<SearchResultBook> book = result.getDocs()
			.stream()
			.limit(10)
			.map(bookResult -> {
				bookResult.setKey(bookResult.getKey().replace("/works/",""));
				String coverId = bookResult.getCover_i();
				if(StringUtils.hasText(coverId)) {
					coverId = COVER_IMAGE_ROOT+coverId+"-M.jpg";
				}else{
					coverId = null;
				}
				bookResult.setCover_i(coverId);
				return bookResult;
			})
			.collect(Collectors.toList());
		return book;
	}
}

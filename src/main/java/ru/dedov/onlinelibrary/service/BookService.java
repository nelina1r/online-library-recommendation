package ru.dedov.onlinelibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dedov.onlinelibrary.dto.BookWithAverageRatingDto;
import ru.dedov.onlinelibrary.dto.BookWithUserRatingDto;
import ru.dedov.onlinelibrary.dto.ListAllBooksWithAverageRatingResponse;
import ru.dedov.onlinelibrary.model.entity.Book;
import ru.dedov.onlinelibrary.model.entity.UserBookRating;
import ru.dedov.onlinelibrary.model.repository.BookRepository;
import ru.dedov.onlinelibrary.model.repository.UserBookRatingRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для книг
 *
 * @author Alexander Dedov
 * @since 19.10.2024
 */
@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;
	private final UserBookRatingRepository userBookRatingRepository;
	private final UserService userService;

	public ListAllBooksWithAverageRatingResponse getAllBooks() {
		List<Book> books = bookRepository.findAll();
		List<BookWithAverageRatingDto> bookWithAverageRatingDtos = books.stream()
			.map(book -> {
				BookWithAverageRatingDto bookWithAverageRatingDto = new BookWithAverageRatingDto();
				bookWithAverageRatingDto.setBook(book);
				List<UserBookRating> bookRatings = userBookRatingRepository.findUserBookRatingsByBook(book);
				Double averageRating = bookRatings.stream()
					.mapToInt(UserBookRating::getRating)
					.average()
					.orElse(0.0);
				bookWithAverageRatingDto.setAverageRating(averageRating);
				return bookWithAverageRatingDto;
			}).toList();
		ListAllBooksWithAverageRatingResponse resultDto = new ListAllBooksWithAverageRatingResponse();
		resultDto.setBooksWithAverageRating(bookWithAverageRatingDtos);
		return resultDto;
	}

	public BookWithUserRatingDto getBookWithRatingById(String id) {
		BookWithUserRatingDto bookWithUserRatingDto = new BookWithUserRatingDto();
		Book book = getBookById(id);
		UserBookRating userBookRating = userBookRatingRepository.findByUserAndBook(userService.getCurrentUser(), book);
		Double rating = userBookRating == null ? 0.0 : userBookRating.getRating();
		bookWithUserRatingDto.setBook(book);
		bookWithUserRatingDto.setRating(rating);
		return bookWithUserRatingDto;
	}

	public Book getBookById(String id) {
		return bookRepository.findById(id).orElseThrow();
	}
}

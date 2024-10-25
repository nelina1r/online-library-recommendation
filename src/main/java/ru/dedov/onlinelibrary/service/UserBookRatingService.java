package ru.dedov.onlinelibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dedov.onlinelibrary.dto.DeleteUserBookRatingRequest;
import ru.dedov.onlinelibrary.dto.UserBookRatingRequest;
import ru.dedov.onlinelibrary.model.entity.Book;
import ru.dedov.onlinelibrary.model.entity.User;
import ru.dedov.onlinelibrary.model.entity.UserBookRating;
import ru.dedov.onlinelibrary.model.repository.UserBookRatingRepository;

/**
 * Сервис для пользовательских оценок по книгам
 *
 * @author Alexander Dedov
 * @since 20.10.2024
 */
@Service
@RequiredArgsConstructor
public class UserBookRatingService {

	private final UserService userService;
	private final BookService bookService;
	private final UserBookRatingRepository userBookRatingRepository;

	@Transactional
	public void saveUserBookRating(UserBookRatingRequest request) {
		User currentUser = userService.getCurrentUser();
		Book book = bookService.getBookById(request.getBookId());
		Integer rating = request.getRating();
		UserBookRating userBookRating = userBookRatingRepository.existsByUserAndBook(currentUser, book)
			? userBookRatingRepository.findByUserAndBook(currentUser, book)
			: new UserBookRating();
		userBookRating.setUser(currentUser);
		userBookRating.setBook(book);
		userBookRating.setRating(rating);
		userBookRatingRepository.save(userBookRating);
	}

	@Transactional
	public void deleteUserBookRating(DeleteUserBookRatingRequest request) {
		Book book = bookService.getBookById(request.getBookId());
		User currentUser = userService.getCurrentUser();
		userBookRatingRepository.deleteByUserAndBook(currentUser, book);
	}
}

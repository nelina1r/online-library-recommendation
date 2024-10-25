package ru.dedov.onlinelibrary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import ru.dedov.onlinelibrary.model.entity.Book;
import ru.dedov.onlinelibrary.model.entity.UserBookRating;
import ru.dedov.onlinelibrary.model.repository.UserBookRatingRepository;

import java.util.*;

/**
 * Сервис для
 *
 * @author Alexander Dedov
 * @since 20.10.2024
 */
@Service
@RequiredArgsConstructor
@Log
public class RecommendationService {

	private final BookService bookService;
	private final UserBookRatingRepository ratingRepository;
	private final UserService userService;

	public List<Book> recommendBooksForUser() {
		Map<String, Map<String, Double>> bookTfIdfVectors = bookService.calculateTfIdfForBooks();
		log.info("Вектора: " + bookTfIdfVectors);
		List<UserBookRating> userRatings = ratingRepository.findByUser(userService.getCurrentUser());

		// Находим книги, которые пользователь оценил высоко
		List<Book> highlyRatedBooks = userRatings.stream()
			.filter(rating -> rating.getRating() >= 4)
			.map(UserBookRating::getBook)
			.toList();
		log.info("Оцененные книги: " + highlyRatedBooks);

		// Находим книги, похожие на те, которые пользователь оценил высоко
		List<Book> recommendedBooks = new ArrayList<>();
		for (Book ratedBook : highlyRatedBooks) {
			Map<String, Double> ratedBookTfIdf = bookTfIdfVectors.get(ratedBook.getId());
			for (Map.Entry<String, Map<String, Double>> entry : bookTfIdfVectors.entrySet()) {
				if (!ratedBook.getId().equals(entry.getKey())) {
					double similarity = cosineSimilarity(ratedBookTfIdf, entry.getValue());
					if (similarity > 0.03) {  // Порог сходства
						Book similarBook = bookService.getBookById(entry.getKey());
						if (!recommendedBooks.contains(similarBook)) {
							recommendedBooks.add(similarBook);
						}
					}
				}
			}
		}
		return recommendedBooks;
	}

	// Метод для расчета косинусного сходства между двумя векторами TF-IDF
	private double cosineSimilarity(Map<String, Double> vec1, Map<String, Double> vec2) {
		Set<String> words = new HashSet<>(vec1.keySet());
		words.addAll(vec2.keySet());

		double dotProduct = 0;
		double magnitude1 = 0;
		double magnitude2 = 0;

		for (String word : words) {
			double v1 = vec1.getOrDefault(word, 0.0);
			double v2 = vec2.getOrDefault(word, 0.0);

			dotProduct += v1 * v2;
			magnitude1 += v1 * v1;
			magnitude2 += v2 * v2;
		}

		if (magnitude1 == 0 || magnitude2 == 0) {
			return 0;
		}

		return dotProduct / (Math.sqrt(magnitude1) * Math.sqrt(magnitude2));
	}
}

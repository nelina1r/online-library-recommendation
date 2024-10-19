package ru.dedov.onlinelibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dedov.onlinelibrary.dto.UserBookRatingRequest;
import ru.dedov.onlinelibrary.service.UserBookRatingService;

/**
 * Контроллер для оценивания книг пользователями
 *
 * @author Alexander Dedov
 * @since 20.10.2024
 */
@RestController
@RequestMapping("/api/v1/rating")
@RequiredArgsConstructor
@Tag(name = "Оценивание книг пользователями")
public class UserBookRatingController {

	private final UserBookRatingService userBookRatingService;

	@Operation(summary = "Оценить книгу")
	@PostMapping("/rate")
	public ResponseEntity<?> createGenre(@RequestBody @Valid UserBookRatingRequest request) {
		userBookRatingService.saveUserBookRating(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

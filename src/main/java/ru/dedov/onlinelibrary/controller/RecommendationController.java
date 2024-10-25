package ru.dedov.onlinelibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dedov.onlinelibrary.service.RecommendationService;

/**
 * Контроллер для рекомендаций
 *
 * @author Alexander Dedov
 * @since 20.10.2024
 */
@RestController
@RequestMapping("/api/v1/recommendation")
@RequiredArgsConstructor
@Tag(name = "Рекомендованные книги")
public class RecommendationController {

	private final RecommendationService recommendationService;

	@Operation(summary = "Получить рекомендованные книги")
	@GetMapping("/list")
	public ResponseEntity<?> listAllBooks() {
		return new ResponseEntity<>(recommendationService.recommendBooksForUser(), HttpStatus.OK);
	}
}

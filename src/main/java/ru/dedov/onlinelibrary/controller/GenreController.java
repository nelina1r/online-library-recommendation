package ru.dedov.onlinelibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dedov.onlinelibrary.dto.CreateGenreRequest;
import ru.dedov.onlinelibrary.service.GenreService;

/**
 * Контроллер для Жанров
 *
 * @author Alexander Dedov
 * @since 19.10.2024
 */
@RestController
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
@Tag(name = "Жанры")
public class GenreController {

	private final GenreService genreService;

	@Operation(summary = "Создать жанр")
	@PostMapping("/create")
	public ResponseEntity<?> createGenre(@RequestBody @Valid CreateGenreRequest request) {
		genreService.saveGenre(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "Получить все жанры")
	@GetMapping("/list")
	public ResponseEntity<?> listAllBooks() {
		return new ResponseEntity<>(genreService.getAllGenres(), HttpStatus.OK);
	}
}

package ru.dedov.onlinelibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dedov.onlinelibrary.service.BookService;

/**
 * Контроллер для книг
 *
 * @author Alexander Dedov
 * @since 19.10.2024
 */
@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "Книги")
public class BookController {

	private final BookService bookService;

	@Operation(summary = "Получить все книги")
	@GetMapping("/list")
	public ResponseEntity<?> listAllBooks() {
		return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
	}

	@Operation(summary = "Получить книгу по id")
	@GetMapping("/{id}")
	public ResponseEntity<?> getBookById(@PathVariable String id) {
		return new ResponseEntity<>(bookService.getBookWithRatingById(id), HttpStatus.OK);
	}
}

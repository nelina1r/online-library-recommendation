package ru.dedov.onlinelibrary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Список книг со средней оценкой по каждой
 *
 * @author Alexander Dedov
 * @since 20.10.2024
 */
@Data
public class ListAllBooksWithAverageRatingResponse {
	@JsonProperty("books_with_average_rating")
	private List<BookWithAverageRatingDto> booksWithAverageRating;
}

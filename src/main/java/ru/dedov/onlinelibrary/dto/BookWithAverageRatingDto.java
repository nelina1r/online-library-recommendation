package ru.dedov.onlinelibrary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.dedov.onlinelibrary.model.entity.Book;

/**
 * Книга со средней оценкой
 *
 * @author Alexander Dedov
 * @since 20.10.2024
 */
@Data
public class BookWithAverageRatingDto {
	private Book book;
	@JsonProperty("average_rating")
	private Double averageRating;
}

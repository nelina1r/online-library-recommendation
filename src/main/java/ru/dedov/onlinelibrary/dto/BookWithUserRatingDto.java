package ru.dedov.onlinelibrary.dto;

import lombok.Data;
import ru.dedov.onlinelibrary.model.entity.Book;

/**
 * Книга с пользовательской оценкой
 *
 * @author Alexander Dedov
 * @since 20.10.2024
 */
@Data
public class BookWithUserRatingDto {

	private Book book;
	private Double rating;
}

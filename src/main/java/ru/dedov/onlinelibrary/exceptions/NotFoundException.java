package ru.dedov.onlinelibrary.exceptions;

/**
 * Исключение выбрасываемое в случае если что-то не найдено
 *
 * @author Alexander Dedov
 * @since 20.10.2024
 */
public class NotFoundException extends RuntimeException {
	public NotFoundException(String message) {
		super(message);
	}
}

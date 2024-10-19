package ru.dedov.onlinelibrary.exceptions;

/**
 * Исключение выбрасываемое в случае если что-то уже существует
 *
 * @author Alexander Dedov
 * @since 19.10.2024
 */
public class AlreadyExistsException extends RuntimeException{
	public AlreadyExistsException(String message) {
		super(message);
	}
}

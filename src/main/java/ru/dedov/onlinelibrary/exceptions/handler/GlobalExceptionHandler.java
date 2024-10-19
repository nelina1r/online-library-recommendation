package ru.dedov.onlinelibrary.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.dedov.onlinelibrary.dto.ErrorResponse;
import ru.dedov.onlinelibrary.exceptions.AlreadyExistsException;
import ru.dedov.onlinelibrary.exceptions.UserAlreadyExistsException;

import java.util.NoSuchElementException;

/**
 * Централизованный обработчик исключений на уровне всего приложения
 *
 * @author Alexander Dedov
 * @since 19.10.2024
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * @param ex исключение, выбрасываемое в случае если не пройдена валидация входных параметров
	 * @return ResponseEntity с ErrorResponse с текстом ошибки и кодом 400
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		String errorMessage = ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
		return new ResponseEntity<>(new ErrorResponse(errorMessage), HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param ex исключение, выбрасываемое в случае если пользователь уже существует
	 * @return ResponseEntity с ErrorResponse с текстом ошибки и кодом 400
	 */
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
		return new ResponseEntity<>(new ErrorResponse(ex.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param ex исключение, выбрасываемое в случае если что-то уже существует
	 * @return ResponseEntity с ErrorResponse с текстом ошибки и кодом 400
	 */
	@ExceptionHandler(AlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleAlreadyExistsException(AlreadyExistsException ex) {
		return new ResponseEntity<>(new ErrorResponse(ex.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param ex исключение, выбрасываемое в случае если чего то не существует
	 * @return ResponseEntity с ErrorResponse с текстом ошибки и кодом 400
	 */
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
		return new ResponseEntity<>(new ErrorResponse(ex.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
	}
}
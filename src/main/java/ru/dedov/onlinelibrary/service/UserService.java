package ru.dedov.onlinelibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.dedov.onlinelibrary.exceptions.UserAlreadyExistsException;
import ru.dedov.onlinelibrary.model.entity.User;
import ru.dedov.onlinelibrary.model.entity.enums.Role;
import ru.dedov.onlinelibrary.model.repository.UserRepository;

/**
 * Сервис для операций над пользователями
 *
 * @author Alexander Dedov
 * @since 13.10.2024
 */
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	/**
	 * Сохранение пользователя
	 *
	 * @return сохраненный пользователь
	 */
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	/**
	 * Создание пользователя
	 *
	 * @return созданный пользователь
	 */
	public User createUser(User user) {
		if (userRepository.existsByUsername(user.getUsername())) {
			// Заменить на свои исключения
			throw new UserAlreadyExistsException("Пользователь с таким именем уже существует");
		}

		if (userRepository.existsByEmail(user.getEmail())) {
			throw new UserAlreadyExistsException("Пользователь с таким email уже существует");
		}

		return saveUser(user);
	}

	/**
	 * Получение пользователя по имени пользователя
	 *
	 * @return пользователь
	 */
	public User getByUsername(String username) {
		return userRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

	}

	/**
	 * Получение пользователя по имени пользователя
	 * <p>
	 * Нужен для Spring Security
	 *
	 * @return пользователь
	 */
	public UserDetailsService userDetailsService() {
		return this::getByUsername;
	}

	/**
	 * Получение текущего пользователя
	 *
	 * @return текущий пользователь
	 */
	public User getCurrentUser() {
		// Получение имени пользователя из контекста Spring Security
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return getByUsername(username);
	}


	/**
	 * Выдача прав администратора текущему пользователю
	 *
	 * Нужен для демонстрации todo: удалить
	 */
	@Deprecated
	public void getAdmin() {
		User user = getCurrentUser();
		user.setRole(Role.ROLE_ADMIN);
		saveUser(user);
	}
}
package ru.dedov.onlinelibrary.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dedov.onlinelibrary.model.entity.User;

import java.util.Optional;

/**
 * Репозиторий для пользователей
 *
 * @author Alexander Dedov
 * @since 13.10.2024
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findByUsername(String username);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);
}

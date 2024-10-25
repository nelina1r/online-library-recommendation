package ru.dedov.onlinelibrary.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dedov.onlinelibrary.model.entity.Book;
import ru.dedov.onlinelibrary.model.entity.User;
import ru.dedov.onlinelibrary.model.entity.UserBookRating;

import java.util.List;

/**
 * Репозиторий для {@see UserBookRating}
 *
 * @author Alexander Dedov
 * @since 19.10.2024
 */
@Repository
public interface UserBookRatingRepository extends JpaRepository<UserBookRating, String> {

	List<UserBookRating> findByUser(User user);

	UserBookRating findByUserAndBook(User user, Book book);

	boolean existsByUserAndBook(User user, Book book);

	List<UserBookRating> findUserBookRatingsByBook(Book book);

	void deleteByUserAndBook(User user, Book book);
}

package ru.dedov.onlinelibrary.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dedov.onlinelibrary.model.entity.Book;

/**
 * Репозиторий для книг
 *
 * @author Alexander Dedov
 * @since 19.10.2024
 */
@Repository
public interface BookRepository extends JpaRepository<Book, String> {
}

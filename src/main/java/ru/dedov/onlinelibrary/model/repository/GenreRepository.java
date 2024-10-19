package ru.dedov.onlinelibrary.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dedov.onlinelibrary.model.entity.Genre;

/**
 * Репозиторий для жанров
 *
 * @author Alexander Dedov
 * @since 19.10.2024
 */
@Repository
public interface GenreRepository extends JpaRepository<Genre, String> {

	boolean existsByNameIgnoreCase(String name);
}

package ru.dedov.onlinelibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dedov.onlinelibrary.dto.CreateGenreRequest;
import ru.dedov.onlinelibrary.exceptions.AlreadyExistsException;
import ru.dedov.onlinelibrary.model.entity.Genre;
import ru.dedov.onlinelibrary.model.repository.GenreRepository;

import java.util.List;

/**
 * Сервис для жанров
 *
 * @author Alexander Dedov
 * @since 19.10.2024
 */
@Service
@RequiredArgsConstructor
public class GenreService {

	private final GenreRepository genreRepository;

	/**
	 * Сохранить новый жанр
	 * @param request DTO с именем жанра
	 */
	@Transactional
	public void saveGenre(CreateGenreRequest request) {
		String genreName = request.getGenreName();
		if (genreRepository.existsByNameIgnoreCase(genreName)) {
			throw new AlreadyExistsException("Жанр с именем " + genreName + " уже существует");
		}
		Genre genre = new Genre();
		genre.setName(genreName);
		genreRepository.save(genre);
	}

	public List<Genre> getAllGenres() {
		return genreRepository.findAll();
	}
}

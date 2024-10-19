package ru.dedov.onlinelibrary.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Сущность "Книга"
 *
 * @author Alexander Dedov
 * @since 19.10.2024
 */
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String url;

	@Column(nullable = false)
	private String description;

	// Связь с авторами (много авторов могут написать одну книгу)
	@ManyToMany
	@JoinTable(name = "book_author",
		joinColumns = @JoinColumn(name = "book_id"),
		inverseJoinColumns = @JoinColumn(name = "author_id"))
	@JsonManagedReference
	private List<Author> authors;

	// Связь с жанрами (книга может относиться к нескольким жанрам)
	@ManyToMany
	@JoinTable(name = "book_genre",
		joinColumns = @JoinColumn(name = "book_id"),
		inverseJoinColumns = @JoinColumn(name = "genre_id"))
	@JsonManagedReference
	private List<Genre> genres;
}

package ru.dedov.onlinelibrary.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Сущность "Автор"
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
@Table(name = "authors")
public class Author {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(nullable = false)
	private String name;

	// Список книг, написанных автором
	@ManyToMany(mappedBy = "authors")
	@JsonBackReference // Обратная сторона связи
	private List<Book> books;
}

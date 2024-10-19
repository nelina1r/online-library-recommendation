package ru.dedov.onlinelibrary.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Сущность "Жанр"
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
@Table(name = "genres")
public class Genre {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(nullable = false)
	private String name;

	// Книги, относящиеся к этому жанру
	@ManyToMany(mappedBy = "genres")
	@JsonBackReference
	private List<Book> books;
}

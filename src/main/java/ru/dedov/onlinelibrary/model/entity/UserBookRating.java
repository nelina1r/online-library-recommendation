package ru.dedov.onlinelibrary.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Сущность - связь пользователя с оценкой книги
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
@Table(name = "userbookratings")
public class UserBookRating {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "book_id", nullable = false)
	private Book book;

	// Оценка книги пользователем (например, от 1 до 5)
	@Column(name = "rating", nullable = false)
	private Integer rating;
}

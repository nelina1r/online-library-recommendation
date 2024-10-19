package ru.dedov.onlinelibrary.service;

import lombok.RequiredArgsConstructor;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;
import org.springframework.stereotype.Service;
import ru.dedov.onlinelibrary.model.entity.Book;
import ru.dedov.onlinelibrary.model.entity.User;
import ru.dedov.onlinelibrary.model.entity.UserBookRating;
import ru.dedov.onlinelibrary.model.repository.BookRepository;
import ru.dedov.onlinelibrary.model.repository.UserBookRatingRepository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Сервис для
 *
 * @author Alexander Dedov
 * @since 20.10.2024
 */
@Service
@RequiredArgsConstructor
public class RecommendationService {

	private final BookRepository bookRepository;
	private final UserBookRatingRepository userBookRatingRepository;
	private final UserService userService;

	public List<Book> recommendBooks() throws IOException {
		// Получаем все книги и их описания
		List<Book> books = bookRepository.findAll();
		// Получаем рейтинги пользователя
		List<UserBookRating> userRatings = userBookRatingRepository.findByUser(userService.getCurrentUser());
		// Создаем векторы TF-IDF для описаний книг
		Map<String, double[]> tfIdfVectors = calculateTfIdf(books);
		// Находим книги, которые пользователь оценил
		List<Book> ratedBooks = userRatings.stream()
			.map(UserBookRating::getBook)
			.collect(Collectors.toList());
		// Создаем список рекомендованных книг
		List<Book> recommendedBooks = new ArrayList<>();
		// Рекомендуем книги на основе косинусного сходства
		for (Book ratedBook : ratedBooks) {
			double[] ratedVector = tfIdfVectors.get(ratedBook.getId());
			if (ratedVector == null) continue; // Проверка на null

			for (Book book : books) {
				if (!ratedBooks.contains(book)) { // Исключаем уже оцененные книги
					double[] otherVector = tfIdfVectors.get(book.getId());
					if (otherVector == null) continue; // Проверка на null

					double similarity = calculateCosineSimilarity(ratedVector, otherVector);
					if (similarity > 0.5) { // Порог схожести (можно настроить)
						recommendedBooks.add(book);
					}
				}
			}
		}
		return recommendedBooks.stream().distinct().collect(Collectors.toList()); // Убираем дубликаты
	}

	private Map<String, double[]> calculateTfIdf(List<Book> books) throws IOException {
		Map<String, double[]> tfIdfVectors = new HashMap<>();

		// Указываем место для индекса
		Directory indexDirectory = new ByteBuffersDirectory();
		IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
		IndexWriter writer = new IndexWriter(indexDirectory, config);

		// Индексируем книги
		for (Book book : books) {
			Document doc = new Document();
			doc.add(new StringField("id", book.getId(), Field.Store.YES));
			doc.add(new TextField("description", book.getDescription(), Field.Store.YES));
			writer.addDocument(doc);
		}
		writer.close();

		// Создаем индексный читатель
		IndexReader reader = DirectoryReader.open(indexDirectory);
		int numDocs = reader.numDocs();

		// Собираем уникальные термины из всех описаний
		Set<String> uniqueTerms = new HashSet<>();
		for (Book book : books) {
			String[] terms = book.getDescription().toLowerCase().split("\\W+"); // Разделение по не-словесным символам
			Collections.addAll(uniqueTerms, terms);
		}

		// Для каждой книги получаем TF-IDF вектор
		for (Book book : books) {
			int docId = Integer.parseInt(book.getId()); // Убедитесь, что ID можно преобразовать
			if (docId < 0 || docId >= numDocs) continue; // Проверка на корректность ID

			// Создаем вектор для этой книги
			double[] tfIdfVector = new double[uniqueTerms.size()];
			int index = 0;

			// Рассчитываем TF-IDF для каждого уникального термина
			for (String term : uniqueTerms) {
				double tf = 0;
				// Получаем термины из документа
				Terms terms = reader.getTermVector(docId, "description");
				if (terms != null) {
					TermsEnum termsEnum = terms.iterator();
					while (termsEnum.next() != null) {
						if (termsEnum.term().utf8ToString().equals(term)) {
							tf = termsEnum.docFreq(); // TF (Term Frequency)
							break;
						}
					}
				}

				double idf = Math.log((double) numDocs / (reader.docFreq(new Term("description", term)) + 1)); // IDF (Inverse Document Frequency)
				tfIdfVector[index++] = tf * idf;
			}

			tfIdfVectors.put(book.getId(), tfIdfVector);
		}

		reader.close();
		indexDirectory.close();

		return tfIdfVectors;
	}

	private double calculateCosineSimilarity(double[] vectorA, double[] vectorB) {
		double dotProduct = 0.0;
		double normA = 0.0;
		double normB = 0.0;

		for (int i = 0; i < vectorA.length; i++) {
			dotProduct += vectorA[i] * vectorB[i];
			normA += Math.pow(vectorA[i], 2);
			normB += Math.pow(vectorB[i], 2);
		}

		return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
	}
}

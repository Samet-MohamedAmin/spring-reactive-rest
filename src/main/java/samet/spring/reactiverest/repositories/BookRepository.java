package samet.spring.reactiverest.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import samet.spring.reactiverest.models.Book;


public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}

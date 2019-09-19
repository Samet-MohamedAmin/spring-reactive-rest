package samet.spring.reactiverest.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import samet.spring.reactiverest.models.Author;


public interface AuthorRepository extends ReactiveMongoRepository<Author, String>{
}

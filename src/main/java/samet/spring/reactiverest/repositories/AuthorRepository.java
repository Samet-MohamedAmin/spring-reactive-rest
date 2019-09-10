package samet.spring.reactiverest.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import samet.spring.reactiverest.models.Author;

/**
 * Created by jt on 12/23/17.
 */
public interface AuthorRepository extends ReactiveMongoRepository<Author, String>{
}

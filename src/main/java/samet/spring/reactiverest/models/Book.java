package samet.spring.reactiverest.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book implements BaseEntity {

    @Id
    private String id;

    private String name;

    @DBRef
    @Builder.Default
    private Set<Author> authors = new HashSet<>();

    public void addAuthor(Author book) {

        authors.add(book);
    }

    public void addAuthor(Mono<Author> book) {

        authors.add(book.block());
    }

    public void addAuthorList(Collection<Author> bookList) {

        authors.addAll(bookList);
    }

    public void addAuthorList(Flux<Author> bookList) {

        authors.addAll(bookList.collectList().block());
    }

}


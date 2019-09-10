package samet.spring.reactiverest.models;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

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
public class BookAuthors {

    @Id
    private String id;

    @DBRef
    private Book book;

    @DBRef
    @Builder.Default
    private Set<Author> authors = new HashSet<>();


    public void addAuthor(Author author) {

        authors.add(author);
    }

    public void addAuthor(Mono<Author> author) {

        authors.add(author.block());
    }

    public void addAuthorList(Collection<Author> authorList) {

        authors.addAll(authorList);
    }

    public void addAuthorList(Flux<Author> authorList) {

        authors.addAll(authorList.collectList().block());
    }
}

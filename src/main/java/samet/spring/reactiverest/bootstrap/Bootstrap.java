package samet.spring.reactiverest.bootstrap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import samet.spring.reactiverest.repositories.AuthorRepository;
import samet.spring.reactiverest.repositories.BookAuthorsRepository;
import samet.spring.reactiverest.repositories.BookRepository;
import samet.spring.reactiverest.models.Author;
import samet.spring.reactiverest.models.Book;
import samet.spring.reactiverest.models.BookAuthors;


@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {

    private BookRepository bookRepository;
    private BookAuthorsRepository bookAuthorsRepository;
    private AuthorRepository authorRepository;

    @Autowired
    public Bootstrap(BookRepository bookRepository,
            BookAuthorsRepository bookAuthorsRepository,
            AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.bookAuthorsRepository = bookAuthorsRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // testReactive();

        if(bookRepository.count().block() == 0){

            log.info("#### LOADING DATA ON BOOTSTRAP #####");

            loadAuthors();
        }
    }

    private void loadAuthors() {

        // var authors = List.of(
        //     Author.builder().name("Joe 1").build(),
        //     Author.builder().name("Joe 2").build(),
        //     Author.builder().name("Joe 3").build()
        // );

        // save books
        var books = List.of(
            Book.builder().name("book 12").build(),
            Book.builder().name("book 23").build(),
            Book.builder().name("book 31").build()
        );

        var bookList = bookRepository.saveAll(books).collectList().block();

        var bookAuthors = new ArrayList<BookAuthors>();
        bookList.forEach(
            book -> bookAuthors.add(BookAuthors.builder().book(book).build())
        );

        // save authors
        var authors = List.of(
            Author.builder().name("Joe 1").build(),
            Author.builder().name("Joe 1").build(),
            Author.builder().name("Joe 3").build()
        );

        authors.get(0).addBook(bookList.get(0));
        authors.get(0).addBook(bookList.get(1));

        authors.get(1).addBook(bookList.get(0));
        authors.get(1).addBook(bookList.get(1));

        authorRepository.saveAll(authors).collectList().block();

        // save bookAuthors
        bookAuthors.get(0).addAuthor(authors.get(0));
        bookAuthors.get(0).addAuthor(authors.get(1));

        bookAuthors.get(1).addAuthor(authors.get(0));
        bookAuthors.get(1).addAuthor(authors.get(1));

        bookAuthorsRepository.saveAll(bookAuthors).collectList().block();


        // log results
        log.info("Loaded Books: " + bookRepository.count().block());
        log.info(bookRepository.findAll().collectList().block().toString());

        log.info("Loaded Authors: " + authorRepository.count().block());
        log.info(authorRepository.findAll().collectList().block().toString());

        log.info("BookAuthors: " + bookAuthorsRepository.count().block());
        log.info(bookAuthorsRepository.findAll().collectList().block().toString());

        log.info(bookAuthorsRepository.findByBook(bookList.get(0)).block().toString());

    }
}

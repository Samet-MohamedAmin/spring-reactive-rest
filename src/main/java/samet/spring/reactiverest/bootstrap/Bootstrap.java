package samet.spring.reactiverest.bootstrap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import samet.spring.reactiverest.models.Author;
import samet.spring.reactiverest.models.Book;
import samet.spring.reactiverest.services.AuthorBooksService;
import samet.spring.reactiverest.services.AuthorService;
import samet.spring.reactiverest.services.BookService;


@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {

    private BookService bookService;
    private AuthorBooksService authorBooksService;
    private AuthorService authorService;

    @Autowired
    public Bootstrap(
            BookService bookService,
            AuthorBooksService authorBooksService,
            AuthorService authorService) {
        this.bookService = bookService;
        this.authorBooksService = authorBooksService;
        this.authorService = authorService;
    }

    @Override
    public void run(String... args) throws Exception {

        // testReactive();

        log.info("#### LOADING DATA ON BOOTSTRAP #####");

        if(authorService.count().block() == 0){

            loadAuthors();
        }

        if(bookService.count().block() == 0){

            loadBooks();
        }
    }

    private void loadBooks() {

        log.info("LOADING BOOKS");

        var authorList = authorService.findAll().collectList().block();

        // save authors
        var books = List.of(
            Book.builder().name("Book 1").build(),
            Book.builder().name("Book 1").build(),
            Book.builder().name("Book 3").build()
        );

        bookService.addAuthor(books.get(0), authorList.get(0)).block();
        bookService.addAuthor(books.get(0), authorList.get(1)).block();

        bookService.addAuthor(books.get(1), authorList.get(0)).block();
        bookService.addAuthor(books.get(1), authorList.get(1)).block();

        log.info("Loaded Books: " + bookService.count().block());
        log.info(bookService.findAll().collectList().block().toString());

        log.info("AuthorBooks: " + authorBooksService.count().block());
        log.info(authorBooksService.findAll().collectList().block().toString());
    }

    private void loadAuthors() {

        log.info("LOADING AUTHORS");

        var authors = Flux.just(
            Author.builder().name("author 12").build(),
            Author.builder().name("author 23").build(),
            Author.builder().name("author 31").build()
        );

        authorService.saveAll(authors).collectList().block();

        log.info("Loaded Authors: " + authorService.count().block());
        log.info(authorService.findAll().collectList().block().toString());

        log.info("AuthorBooks: " + authorBooksService.count().block());
        log.info(authorBooksService.findAll().collectList().block().toString());
    }
}

package samet.spring.reactiverest.bootstrap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import samet.spring.reactiverest.repositories.AuthorRepository;
import samet.spring.reactiverest.repositories.AuthorBooksRepository;
import samet.spring.reactiverest.repositories.BookRepository;
import samet.spring.reactiverest.models.Author;
import samet.spring.reactiverest.models.Book;
import samet.spring.reactiverest.models.AuthorBooks;


@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {

    private BookRepository bookRepository;
    private AuthorBooksRepository authorBooksRepository;
    private AuthorRepository authorRepository;

    @Autowired
    public Bootstrap(BookRepository bookRepository,
            AuthorBooksRepository authorBooksRepository,
            AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorBooksRepository = authorBooksRepository;
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

        // save books
        var authors = List.of(
            Author.builder().name("author 12").build(),
            Author.builder().name("author 23").build(),
            Author.builder().name("author 31").build()
        );

        var authorList = authorRepository.saveAll(authors).collectList().block();

        var authorBooks = new ArrayList<AuthorBooks>();
        authorList.forEach(
            author -> authorBooks.add(AuthorBooks.builder().author(author).build())
        );

        // save authors
        var books = List.of(
            Book.builder().name("Book 1").build(),
            Book.builder().name("Book 1").build(),
            Book.builder().name("Book 3").build()
        );

        books.get(0).addAuthor(authorList.get(0));
        books.get(0).addAuthor(authorList.get(1));

        books.get(1).addAuthor(authorList.get(0));
        books.get(1).addAuthor(authorList.get(1));

        bookRepository.saveAll(books).collectList().block();

        // save authorBooks
        authorBooks.get(0).addBook(books.get(0));
        authorBooks.get(0).addBook(books.get(1));

        authorBooks.get(1).addBook(books.get(0));
        authorBooks.get(1).addBook(books.get(1));

        authorBooksRepository.saveAll(authorBooks).collectList().block();


        // log results
        log.info("Loaded Books: " + bookRepository.count().block());
        log.info(bookRepository.findAll().collectList().block().toString());

        log.info("Loaded Authors: " + authorRepository.count().block());
        log.info(authorRepository.findAll().collectList().block().toString());

        log.info("AuthorBooks: " + authorBooksRepository.count().block());
        log.info(authorBooksRepository.findAll().collectList().block().toString());

        log.info(authorBooksRepository.findByAuthor(authorList.get(0)).block().toString());

    }
}

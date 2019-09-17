package samet.spring.reactiverest.bootstrap;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import samet.spring.reactiverest.models.Author;
import samet.spring.reactiverest.models.Book;
import samet.spring.reactiverest.models.ItemStore;
import samet.spring.reactiverest.services.AuthorBooksService;
import samet.spring.reactiverest.services.AuthorService;
import samet.spring.reactiverest.services.BookService;
import samet.spring.reactiverest.services.ItemStoreService;


@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {

    private BookService bookService;
    private AuthorBooksService authorBooksService;
    private AuthorService authorService;
    private ItemStoreService itemStoreService;

    @Autowired
    public Bootstrap(
            BookService bookService,
            AuthorService authorService,
            AuthorBooksService authorBooksService,
            ItemStoreService itemStoreService) {
        this.bookService = bookService;
        this.authorBooksService = authorBooksService;
        this.authorService = authorService;
        this.itemStoreService = itemStoreService;
    }

    @Override
    public void run(String... args) throws Exception {

        // testReactive();

        log.info("#### LOADING DATA ON BOOTSTRAP #####");

        if(authorService.count().block() == 0){

            loadAuthors();
        }

        if(bookService.count().block() == 0){

            loadItemStore();
        }
    }

    private Flux<Author> generateAuthorList() {

        var authors = Flux.just(
            Author.builder().name("author 12").build(),
            Author.builder().name("author 23").build(),
            Author.builder().name("author 31").build()
        );

        return authors;
    }

    private Flux<Book> generateBookList(){

        var bookStream = Flux.just(
            Book.builder().name("Book 1").build(),
            Book.builder().name("Book 2").build(),
            Book.builder().name("Book 3").build()
        );

        var books = bookStream.collectList().block();

        var authors = authorService.findAll().collectList().block();


        bookService.addAuthor(books.get(0), authors.get(0)).block();
        bookService.addAuthor(books.get(0), authors.get(1)).block();

        bookService.addAuthor(books.get(1), authors.get(0)).block();
        bookService.addAuthor(books.get(1), authors.get(1)).block();

        return bookStream;
    }

    private Flux<ItemStore> generateItemStoreList() {

        var books = generateBookList().collectList().block();

        var itemStoreList = Flux.just(
            ItemStore.builder().book(books.get(0)).quantity(3).build(),
            ItemStore.builder().book(books.get(1)).quantity(6).build()
        );
        return itemStoreList;
    }

    private void loadItemStore() {

        log.info("LOADING ITEMSTORES");

        var itemStoreList = generateItemStoreList();

        log.info(prettifyListLog("ItemStoreList:", itemStoreList.collectList().block()));
        itemStoreService.saveAll(itemStoreList).collectList().block();

        log.info("ItemStores: " + itemStoreService.count().block());
        log.info(prettifyListLog("ItemStoreList", itemStoreService.findAll().collectList().block()));

        log.info("Books: " + bookService.count().block());
        log.info(prettifyListLog("BooksList", bookService.findAll().collectList().block()));

        log.info("AuthorBooks: " + authorBooksService.count().block());
        log.info(prettifyListLog("AuthorBooksList", authorBooksService.findAll().collectList().block()));
    }

    private void loadAuthors() {

        log.info("LOADING AUTHORS");

        var authors = generateAuthorList();

        authorService.saveAll(authors).collectList().block();

        log.info("Loaded Authors: " + authorService.count().block());
        log.info(prettifyListLog("Authors: ", authorService.findAll().collectList().block()));

        log.info("AuthorBooks: " + authorBooksService.count().block());
        log.info(prettifyListLog("AuthorBooks: ", authorBooksService.findAll().collectList().block()));
    }

    private String prettifyListLog(String name, Collection<?> list) {

		String msg = list.stream()
							.map(Object::toString)
							.reduce((a, b) -> a+ "\n\t" + b)
							.orElseGet(() -> "EMPTY LIST");

		return name + "\n\t" + msg;
	}
}

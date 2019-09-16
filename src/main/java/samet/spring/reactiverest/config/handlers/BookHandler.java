package samet.spring.reactiverest.config.handlers;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import samet.spring.reactiverest.config.PathUtils;
import samet.spring.reactiverest.models.Book;
import samet.spring.reactiverest.repositories.BookRepository;


@Service
public class BookHandler implements Handler {


    private final BookRepository bookRepository;
    private Handler handler;

    public BookHandler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;

        this.handler = new EntityHandler<Book>(this.bookRepository,
                                Book.class,
                                getEntityPath());
    }

    public static String getEntityPath() {

        return PathUtils.BOOKS.getPath();
    }

    public Mono<ServerResponse> list(ServerRequest request) {

        return handler.list(request);
    }

    public Mono<ServerResponse> getById(ServerRequest request){

        return handler.getById(request);
    }

    public Mono<ServerResponse> create(ServerRequest request){
        
        return handler.create(request);
    }

    public Mono<ServerResponse> update(ServerRequest request) {

        return handler.update(request);
    }

    // public Mono<ServerResponse> patch(ServerRequest request) {

    //     return handler.patch(request);
    // }
}

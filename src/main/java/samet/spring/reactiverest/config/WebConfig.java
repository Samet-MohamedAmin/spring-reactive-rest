package samet.spring.reactiverest.config;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import samet.spring.reactiverest.config.handlers.AuthorBooksHandler;
import samet.spring.reactiverest.config.handlers.AuthorHandler;
import samet.spring.reactiverest.config.handlers.BookHandler;
import samet.spring.reactiverest.config.handlers.Handler;
import samet.spring.reactiverest.config.handlers.ItemStoreHandler;


@Configuration
public class WebConfig {

    private Handler bookHandler;
    private Handler authorHandler;
    private Handler authorBooksHandler;
    private Handler itemStoreHandler;

    @Autowired
    WebConfig(
                Handler bookHandler, 
                Handler authorHandler,
                Handler authorBooksHandler,
                Handler itemStoreHandler) {

        this.bookHandler = bookHandler;
        this.authorHandler = authorHandler;
        this.authorBooksHandler = authorBooksHandler;
        this.itemStoreHandler = itemStoreHandler;
    }


    @Bean
    RouterFunction<ServerResponse> authorRouter() {

        final String authorUrl = Handler.getApiBase() + AuthorHandler.getEntityPath();

        final var JSON = accept(APPLICATION_JSON);

        return RouterFunctions.route()
                    .GET(authorUrl, JSON, authorHandler::list)
                    .GET(authorUrl + "/{id}", JSON, authorHandler::getById)
                    .POST(authorUrl, JSON, authorHandler::create)
                    .PUT(authorUrl + "/{id}", JSON, authorHandler::update)
                    // .PATCH(authorUrl + "/{id}", JSON, authorHandler::patch)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> bookRouter() {

        final String booksUrl = Handler.getApiBase() + BookHandler.getEntityPath();

        final var JSON = accept(APPLICATION_JSON);

        return RouterFunctions.route()
                    .GET(booksUrl, JSON, bookHandler::list)
                    .GET(booksUrl + "/{id}", JSON, bookHandler::getById)
                    .POST(booksUrl, JSON, bookHandler::create)
                    .PUT(booksUrl + "/{id}", JSON, bookHandler::update)
                    // .PATCH(booksUrl + "/{id}", JSON, bookHandler::patch)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> authorBooksRouter() {

        final String authorBooksUrl = Handler.getApiBase() + AuthorBooksHandler.getEntityPath();

        final var JSON = accept(APPLICATION_JSON);

        return RouterFunctions.route()
                    .GET(authorBooksUrl, JSON, authorBooksHandler::list)
                    .GET(authorBooksUrl + "/{id}", JSON, authorBooksHandler::getById)
                    .PUT(authorBooksUrl + "/{id}", JSON, authorBooksHandler::update)
                    // .PATCH(booksUrl + "/{id}", JSON, authorBooksHandler::patch)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> itemStoreRouter() {

        final String itemStoreUrl = Handler.getApiBase() + ItemStoreHandler.getEntityPath();

        final var JSON = accept(APPLICATION_JSON);

        return RouterFunctions.route()
                    .GET(itemStoreUrl, JSON, itemStoreHandler::list)
                    .GET(itemStoreUrl + "/{id}", JSON, itemStoreHandler::getById)
                    .PUT(itemStoreUrl + "/{id}", JSON, itemStoreHandler::update)
                    // .PATCH(booksUrl + "/{id}", JSON, itemStoreHandler::patch)
                .build();
    }
}
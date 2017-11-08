package com.pluralsight.bookstore.rest;

import com.pluralsight.bookstore.model.Book;
import com.pluralsight.bookstore.repository.BookRepository;
import com.sun.org.apache.regexp.internal.RESyntaxException;
import io.swagger.annotations.*;

import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.awt.*;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@SwaggerDefinition(
        info = @Info(
                title = "BookStore APIs",
                description = "BookStore APIs exposed from a Java EE back-end to an Angular front-end",
                version = "V1.0.0",
                contact = @Contact(
                        name = "Bishal Upadhayay",
                        email = "bishal.u87@gmail.com"
                )
        ),
        host = "localhost:8080",
        basePath = "/bookstore-back/api",
        schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
        tags = {
                @Tag(name = "Book", description = "Tag used to denote operations as private")
        }
)
@Path("/books")
@Api("Book")
public class BookEndPoint {

    @GET
    @Path("/{id: \\d+}")
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Returns a book given an identifier", response = Book.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Book found"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    public Response getBook(@PathParam("id") @Min(1) Long id) {
        Book book =  bookRepository.find(id);

        if(book == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(book).build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @ApiOperation("Creates a book given a JSon Book representation")
    @ApiResponses({
            @ApiResponse(code = 201, message = "The book is created"),
            @ApiResponse(code = 415, message = "Format is not JSon")
    })
    public Response createBook(@ApiParam(value = "Book to be created", required = true) Book book, @Context UriInfo uriInfo) {
        book = bookRepository.create(book);
        URI createdURI = uriInfo.getBaseUriBuilder().path((book.getId()).toString()).build();
        return Response.created(createdURI).build();
    }

    @DELETE
    @Path("/{id : \\d+}")
    @ApiOperation("Deletes a book given an id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "<Book Name> has been deleted"),
            @ApiResponse(code = 400, message = "Invalid input. Id cannot be lower than 1"),
            @ApiResponse(code = 500, message = "Book not found")
    })
    public Response deleteBook(@PathParam("id") @Min(1) Long id) {
        String bookName = bookRepository.find(id).getTitle();
        String message = "'"+bookName +"'" + " has been deleted";
        bookRepository.delete(id);
        return Response.ok().entity(message).build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Returns all the books", response = Book.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Books found"),
            @ApiResponse(code = 204, message = "No books found"),
    })
    public Response getBooks() {
        List<Book> books = bookRepository.findAll();
        if(books.size() == 0){
            return Response.noContent().build();
        }

        return Response.ok(books).build();
    }

    @GET
    @Path("/count")
    @Produces(TEXT_PLAIN)
    @ApiOperation(value = "Returns the total count of books")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Number of books found"),
            @ApiResponse(code = 204, message = "No books found")
    })
    public Response countBooks() {
        Long ngOfBooks = bookRepository.countAll();

        if(ngOfBooks == 0) {
            return Response.noContent().build();
        }
        return Response.ok(ngOfBooks).build();
    }

    @Inject
    private BookRepository bookRepository;

}

package com.pluralsight.bookstore.repository;

import com.pluralsight.bookstore.model.Book;
import com.pluralsight.bookstore.model.Language;
import com.pluralsight.bookstore.util.IsbnGenerator;
import com.pluralsight.bookstore.util.NumberGenerator;
import com.pluralsight.bookstore.util.TextUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class BookRepositoryTest {

    @Inject
    private BookRepository bookRepository;

    @Test(expected = Exception.class)
    public void findWithInvalidId() {
        bookRepository.find(null);
    }

    @Test(expected = Exception.class)
    public void createInvalidBook() {
        Book book = new Book(null, "description", 123F, "12f", new Date(), 20, "http://www.mypic.com", Language.ENGLISH);
        bookRepository.create(book);
    }

    @Test
    public void create() throws Exception {
        // Test counting books
        assertEquals(Long.valueOf(0), bookRepository.countAll());
        assertEquals(0, bookRepository.findAll().size());

        // Create a book
        Book book = new Book("my  book", "description", 123F, "12f", new Date(), 20, "http://www.mypic.com", Language.ENGLISH);
        bookRepository.create(book);
        Long bookId = book.getId();

        // Check book has been created
        assertNotNull(bookId);
        assertEquals(1, bookRepository.findAll().size());

        // Find created book
        Book bookFound = bookRepository.find(bookId);

        // Check found book
        assertEquals("my book", bookFound.getTitle());
        assertTrue(bookFound.getIsbn().startsWith("13"));

        // Delete book
        bookRepository.delete(bookId);
        assertEquals(Long.valueOf(0), bookRepository.countAll());
        assertEquals(0, bookRepository.findAll().size());


    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(BookRepository.class)
                .addClass(Book.class)
                .addClass(Language.class)
                .addClass(TextUtil.class)
                .addClass(NumberGenerator.class)
                .addClass(IsbnGenerator.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml");
    }

}

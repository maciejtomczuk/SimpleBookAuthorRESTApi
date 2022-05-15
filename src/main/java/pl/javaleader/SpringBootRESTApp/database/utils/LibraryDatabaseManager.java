package pl.javaleader.SpringBootRESTApp.database.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.javaleader.SpringBootRESTApp.model.Author;
import pl.javaleader.SpringBootRESTApp.model.Book;
import pl.javaleader.SpringBootRESTApp.repositories.AuthorRepository;
import pl.javaleader.SpringBootRESTApp.repositories.BookRepository;

@Component
@AllArgsConstructor
public class LibraryDatabaseManager {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public void addBookToDatabase(Book book) {bookRepository.save(book);}

    public void removeBookFromDatabase(Book book) {bookRepository.delete(book);}

    public Iterable<Book> getAllBooksFromDatabase() {return bookRepository.findAll();}

    public Book getBookById(Long id) {return bookRepository.findById(id).orElseGet(() -> null);}

    public void deleteBookById(Long id) {bookRepository.deleteById(id);}

    public Author getAuthorById(Long id) {return authorRepository.findById(id).orElseGet(() -> null);}
}

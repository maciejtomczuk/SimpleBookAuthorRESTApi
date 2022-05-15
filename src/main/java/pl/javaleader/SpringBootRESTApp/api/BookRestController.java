package pl.javaleader.SpringBootRESTApp.api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.javaleader.SpringBootRESTApp.database.utils.LibraryDatabaseManager;
import pl.javaleader.SpringBootRESTApp.model.Author;
import pl.javaleader.SpringBootRESTApp.model.Book;

import java.net.URI;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class BookRestController {

    private LibraryDatabaseManager libraryDatabaseManager;

    @GetMapping("/findAllBooksInLibrary")
    public ResponseEntity<Iterable<Book>> findAllBooksInLibrary() {
        return ResponseEntity.ok(libraryDatabaseManager.getAllBooksFromDatabase());
    }

    @GetMapping("/getBookById/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(libraryDatabaseManager.getBookById(id));
    }

    @PostMapping("/addNewBook")
    public ResponseEntity addNewBook(@RequestBody Book book) {

        Optional<Long> authorId = Optional.ofNullable(book.getAuthor().getId());

        if(authorId.isPresent()) {
            Optional<Author> optionalAuthor = isAuthorExistInDatabase(authorId.get());
            if(optionalAuthor.isPresent()){
                Author author = optionalAuthor.get();

                //create Bidirectional relationship
                author.addBook(book);
                book.setAuthor(author);
            } else {
                Author newAuthor = createNewAuthor(book.getAuthor() .getName(), book.getAuthor().getSurname());
                book.setAuthor(newAuthor);

            }
        }

        libraryDatabaseManager.addBookToDatabase(book);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/getBookById")
                .path("/{id}")
                .buildAndExpand(book.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    private Optional<Author> isAuthorExistInDatabase(Long authorId) {
        return Optional.ofNullable(libraryDatabaseManager.getAuthorById(authorId));
    }

    private Author createNewAuthor(String name, String surname) {
        return new Author(name, surname);
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity updateBookById(@RequestBody Book book, @PathVariable Long id) {

        Optional<Book> bookOptional = Optional.ofNullable(libraryDatabaseManager.getBookById(id));

        if(bookOptional.isPresent()) {
            Book updateBook = bookOptional.get();
            updateBook.setAuthor(book.getAuthor());
            updateBook.setTitle(book.getTitle());
            libraryDatabaseManager.addBookToDatabase(book);
        } else {
            libraryDatabaseManager.addBookToDatabase(book);
        }

        return ResponseEntity.ok("The book has been successfully updated");
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity deleteBookById(@PathVariable Long id) {

        libraryDatabaseManager.deleteBookById(id);
        return ResponseEntity.noContent().build();

    }


}

package pl.javaleader.SpringBootRESTApp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.javaleader.SpringBootRESTApp.model.Author;

import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author,Long> {
}

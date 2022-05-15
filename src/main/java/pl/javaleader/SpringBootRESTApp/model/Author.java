package pl.javaleader.SpringBootRESTApp.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;

    private String name;
    private String surname;

    @OneToMany(mappedBy = "author", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private Set<Book> books = new HashSet<>();

    public Author(String name, String surname){
        this.name = name;
        this.surname = surname;
    }

    public void addBook(Book book) {
        books.add(book);
    }

}

package ru.maxima.libra.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="person_id", referencedColumnName = "id")
    @JsonBackReference
    private Person person;


   // @NotEmpty(message = "Название книги не может быть пустым")
   // @Size(min = 2,max = 50,message = "Название книги не может быть не менее 2 символов и не более 50 символов")
    @Column(name = "name")
    private String name;
   // @Min(value = 1900,message = "Год издания книги должен быть больше 1900 года")
    @Column(name = "year_of_production")
    private Integer yearOfProduction;
   // @NotEmpty(message = "Имя автора не может быть пустым")
  //  @Size(min = 2,max = 50,message = "Имя автора не может быть не менее 2 символов и не более 50 символов")
    @Column(name = "author")
    private String author;
   // @NotEmpty(message = "Поле описание не может быть пустым")
   // @Size(min = 2,message = "Поле описание не может быть менее 2 символов")
    @Column(name = "annotation")
    private String annotation;
    @Column(name = "removed")
    private boolean removed;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "removed_at")
    private LocalDateTime removedAt;
    @Column(name = "created_person")
    private String createdPerson;
    @Column(name = "updated_person")
    private String updatedPerson;
    @Column(name = "removed_person")
    private String removedPerson;


    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "Image_id", referencedColumnName = "id")
    private ImageData image;
}

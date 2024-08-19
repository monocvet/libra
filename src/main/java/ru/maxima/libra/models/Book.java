package ru.maxima.libra.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
    private Person person;

    @Column(name = "name")
    private String name;
    @Column(name = "year_of_production")
    private Integer yearOfProduction;
    @Column(name = "author")
    private String author;
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

package ru.maxima.libra.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Book> personBooks;

    //@NotEmpty(message = "Имя не может быть пустым")
   // @Size(min = 4,max = 50,message = "Фамилия, имя и отчество должны быть не менее 5 символов и не более 50 символов")
    @Column(name = "name")
    private String name;

   // @Min(value = 5,message = "Возраст не может быть меньше 5 лет")
    @Column(name = "age")
    private Integer age;
  //  @NotEmpty(message = "Поле электронной почты не может быть пустым")
    @Column(name = "email")
    private String email;
  //  @NotEmpty(message = "Поле номера телефона не может быть пустым")
    @Column(name = "phone_number")
    private String phoneNumber;
  //  @NotEmpty(message = "Поле пароля не может быть пустым")
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
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

}

package ru.maxima.libra.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.maxima.libra.models.Book;

import java.util.List;

@Data
public class PersonDTO {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private String phoneNumber;
    private String role;
}

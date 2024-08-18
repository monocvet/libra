package ru.maxima.libra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PersonDTO {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private String phoneNumber;
    private String role;
    private BookDTO bookDTO;

}

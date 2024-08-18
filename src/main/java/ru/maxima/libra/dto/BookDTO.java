package ru.maxima.libra.dto;

import lombok.*;

@Data
@NoArgsConstructor
@Setter
@Getter
@ToString
public class BookDTO {
    private Long Id;
    private String name;
    private Integer yearOfProduction;
    private String author;
    private String annotation;
    private ImageDataDTO image;
    private OwnerDTO person;

}

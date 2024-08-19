package ru.maxima.libra.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

@Data
public class BookDTO {
    private Long Id;
    private String name;
    private Integer yearOfProduction;
    private String author;
    private String annotation;
    private ImageDataDTO image;
    private OwnerDTO person;

}

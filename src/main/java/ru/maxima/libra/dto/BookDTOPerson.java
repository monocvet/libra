package ru.maxima.libra.dto;

import lombok.Data;

@Data
public class BookDTOPerson {
    private Long Id;
    private String name;
    private Integer yearOfProduction;
    private String author;
}

package ru.maxima.libra.exceptions;

import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Response {
    private String message;
    private Date timestamp;
}

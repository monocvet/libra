package ru.maxima.libra.util;

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

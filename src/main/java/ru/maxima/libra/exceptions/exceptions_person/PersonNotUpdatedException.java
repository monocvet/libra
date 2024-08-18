package ru.maxima.libra.exceptions.exceptions_person;

public class PersonNotUpdatedException  extends RuntimeException {
    public PersonNotUpdatedException(String message) {
        super(message);
    }
}
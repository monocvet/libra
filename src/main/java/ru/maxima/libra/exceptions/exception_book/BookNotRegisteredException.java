package ru.maxima.libra.exceptions.exception_book;

public class BookNotRegisteredException extends RuntimeException {
    public BookNotRegisteredException(String message) {
        super(message);
    }
}

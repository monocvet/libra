package ru.maxima.libra.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maxima.libra.dto.BookDTO;
import ru.maxima.libra.exceptions.NotFoundException;
import ru.maxima.libra.models.Book;
import ru.maxima.libra.models.ImageData;
import ru.maxima.libra.models.Person;
import ru.maxima.libra.repositories.BookRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final PersonService personService;

    @Autowired
    public BookService(BookRepository bookRepository, ModelMapper modelMapper, PersonService personService) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.personService = personService;
    }

    @Transactional
    public List<Book> getAllBooks() {
        return bookRepository.findAllByRemovedIsFalseOrderById();
    }

    @Transactional
    public Book getBook(Long id) {
        Optional<Book> foundBook = Optional.ofNullable(bookRepository.findById(id).orElse(null));
        return foundBook.orElseThrow(() -> new NotFoundException());
    }

    @Transactional
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(Long id, Book updatedBook) {
        if (updatedBook != null) {
            Book book = getBook(id);
            if (book == null) {
                return null;
            }
            book.setName(updatedBook.getName());
            book.setAuthor(updatedBook.getAuthor());
            book.setAnnotation(updatedBook.getAnnotation());
            book.setYearOfProduction(updatedBook.getYearOfProduction());
            book.setUpdatedAt(LocalDateTime.now());
            //book.setUpdatedPerson(getUserName());

            bookRepository.save(book);
            return book;
        } else {
            return null;
        }
    }

    @Transactional
    public void delete(Long id) {
        Book book = getBook(id);
        //book.setRemovedPerson(getUserName());
        book.setRemovedAt(LocalDateTime.now());
        book.setRemoved(true);
        bookRepository.save(book);
    }
    public void freeBook(Long bookId) {
        Book bookById = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Книга не найдена"));
        if (bookById.getPerson() == null) {
            throw new IllegalStateException("Книга свободна");
        }
        bookById.setPerson(null);
        bookRepository.save(bookById);
    }

    @Transactional
    public Book addBookInUser(Long id, Long bookId) {
        Book book = getBook(bookId);
        Person person = personService.getPerson(id);
        if (book != null && person != null) {
            book.setPerson(person);
            bookRepository.save(book);
            return book;
        } else {
            return null;
        }
    }


    public BookDTO convertToBookDTO(Book book) {
        return modelMapper.map(book, BookDTO.class);
    }

    public Book convertToBook(BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        enrich(book);
        return book;
    }

    private void enrich(Book book) {
        book.setCreatedAt(LocalDateTime.now());
        book.setRemoved(false);
    }
}

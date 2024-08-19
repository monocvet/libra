package ru.maxima.libra.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.maxima.libra.dto.BookDTO;
import ru.maxima.libra.exceptions.*;
import ru.maxima.libra.models.Book;
import ru.maxima.libra.repositories.PersonRepository;
import ru.maxima.libra.service.BookService;
import ru.maxima.libra.service.PersonService;
import ru.maxima.libra.exceptions.Response;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/books")
public class BookController {

    private final BookService bookService;
    private final PersonService personService;
    private final PersonRepository personRepository;

    @Autowired
    public BookController(BookService bookService, PersonService personService, PersonRepository personRepository) {
        this.bookService = bookService;
        this.personService = personService;
        this.personRepository = personRepository;
    }

    @GetMapping()
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks()
                .stream()
                .map(bookService::convertToBookDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookDTO getBook(@PathVariable("id") Long id) {
        return bookService.convertToBookDTO(bookService.getBook(id));
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> saveBook(@RequestBody @Valid BookDTO bookDTO,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError :
                    fieldErrors) {
                stringBuilder.append(fieldError.getField())
                        .append(" - ")
                        .append(fieldError.getDefaultMessage())
                        .append(";");
            }
            throw new NotCreatedException(stringBuilder.toString());
        }
        bookService.saveBook(bookService.convertToBook(bookDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<BookDTO> updateBook(@RequestBody @Valid Book updatedBook,
                                              @PathVariable Long id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder bld = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> {
                bld.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            });
            throw new NotUpdatedException(bld.toString());
        }
        Book book = bookService.getBook(id);
        if (book == null) {
            throw new NotFoundException();
        }
        Book newBook = bookService.updateBook(id, updatedBook);
        return ResponseEntity.accepted().body(bookService.convertToBookDTO(newBook));
    }


    @PostMapping("/delete/{id}")
    public ResponseEntity<Response> deleteBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        if (book == null) {
            throw new NotDeletedException();
        }
        bookService.delete(id);
        return ResponseEntity.accepted().body(new Response("Удаление прошло успешно", new Date()));
    }


    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "Такой книги нет", new Date());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({ImageNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleException(ImageNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "Обложка не найдена", new Date());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({NotUpdatedException.class})
    public ResponseEntity<ErrorResponse> handleException(NotUpdatedException e) {
        ErrorResponse response = new ErrorResponse(
                "Книга не обновлена", new Date());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotDeletedException.class})
    public ResponseEntity<ErrorResponse> handleException(NotDeletedException e) {
        ErrorResponse response = new ErrorResponse(
                "Удаляемой книги нет в списке", new Date());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NotCreatedException.class})
    public ResponseEntity<ErrorResponse> handleException(NotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                "Книга не создана", new Date());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/free/{id}")
    public ResponseEntity<String> freeBook(@PathVariable("id") Long Id) {
        try {
            bookService.freeBook(Id);
            return ResponseEntity.ok("Книга " + Id + " свободна!");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/select/{id}")
    public ResponseEntity<BookDTO> addBookInUser(@RequestParam("id") Long book_id, @RequestParam Long id) {
        Book book = bookService.getBook(book_id);
        if (book == null) {
            throw new NotFoundException();
        }
        Book newBook = bookService.addBookInUser(book_id, id);
        return ResponseEntity.accepted().body(bookService.convertToBookDTO(newBook));
    }

    @GetMapping("/{bookid}/image")
    public ResponseEntity<?> downloadImage(@PathVariable("bookid") Long id) {
        byte[] imageData = null;
        try {
            imageData = bookService.downloadImageFromBook(id);
        } catch (Exception e) {
            throw new ImageNotFoundException();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }

}


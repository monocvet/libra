package ru.maxima.libra.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.maxima.libra.dto.BookDTO;
import ru.maxima.libra.exceptions.ErrorResponse;
import ru.maxima.libra.exceptions.NotCreatedException;
import ru.maxima.libra.exceptions.NotDeletedException;
import ru.maxima.libra.exceptions.NotFoundException;
import ru.maxima.libra.exceptions.exception_book.*;
import ru.maxima.libra.models.Book;
import ru.maxima.libra.repositories.PersonRepository;
import ru.maxima.libra.service.BookService;
import ru.maxima.libra.service.PersonService;
import ru.maxima.libra.util.Response;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
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
            throw new BookNotUpdatedException(bld.toString());
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

    @ExceptionHandler({BookNotRegisteredException.class})
    public ResponseEntity<ErrorResponse> handleException(BookNotRegisteredException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(), new Date());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BookNotUpdatedException.class})
    public ResponseEntity<ErrorResponse> handleException(BookNotUpdatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(), new Date());
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
//    @GetMapping("/{bookId}/coverImage")
//    public ResponseEntity<byte[]> getCoverImage(@PathVariable("bookId") Long bookId, @RequestParam("personId") Long personId ) {
//        try {
//            bookService.getCoverImage(bookId, personId);
//        } catch (IllegalStateException ex) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage().getBytes());
//        }
//
//        Book book = bookService.findBookById(bookId);
//
//        if (book == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        byte[] imageData = book.getCoverImage();
//
//        if (imageData == null || imageData.length == 0) {
//            return ResponseEntity.notFound().build();
//        }
//
//        try {
//            String contentType = getContentType(imageData);
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.valueOf(contentType));
//            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
//        } catch (MagicMatchNotFoundException | MagicException | MagicParseException ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

}


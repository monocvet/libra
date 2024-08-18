package ru.maxima.libra.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.maxima.libra.dto.PersonDTO;
import ru.maxima.libra.exceptions.ErrorResponse;
import ru.maxima.libra.exceptions.NotCreatedException;
import ru.maxima.libra.exceptions.NotDeletedException;
import ru.maxima.libra.exceptions.NotFoundException;
import ru.maxima.libra.exceptions.exceptions_person.*;
import ru.maxima.libra.models.Person;
import ru.maxima.libra.service.PersonService;
import ru.maxima.libra.util.Response;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonController(PersonService personService, ModelMapper modelMapper) {
        this.personService = personService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<PersonDTO> getAllPeople() {
        return personService.getAllPeople()
                .stream()
                .map(personService::convertToPersonDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable("id") Long id) {
        return personService.convertToPersonDTO(personService.getPerson(id));

    }

    @PostMapping()
    public ResponseEntity<HttpStatus> savePerson(@RequestBody @Valid PersonDTO personDTO,
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
        personService.savePerson(personService.convertToPerson(personDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<PersonDTO> updatePerson( @RequestBody @Valid Person updatedPerson,
                                                   @PathVariable Long id,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder bld = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> {
                bld.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            });
            throw new PersonNotUpdatedException(bld.toString());
        }
        Person person = personService.getPerson(id);
        if(person  == null){
            throw new NotFoundException();
        }
        Person newPerson = personService.updatePerson(id,updatedPerson);
        return ResponseEntity.accepted().body(personService.convertToPersonDTO(newPerson));
    }


    @PostMapping("/delete/{id}")
    public ResponseEntity<Response> deletePerson(@PathVariable Long id) {
        Person person = personService.getPerson(id);
        if (person == null) {
            throw new NotDeletedException();
        }
        personService.delete(id);
        return ResponseEntity.accepted().body(new Response("Удаление прошло успешно", new Date()));
    }


    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorResponse> handleException(NotFoundException ex) {
        ErrorResponse response = new ErrorResponse(
                "Пользователь не найден", new Date()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NotCreatedException.class})
    public ResponseEntity<ErrorResponse> handleException(NotCreatedException ex) {
        ErrorResponse response = new ErrorResponse(
                "Пользователь не создан", new Date()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PersonNotUpdatedException.class})
    public ResponseEntity<ErrorResponse> handleException(PersonNotUpdatedException e) {
        ErrorResponse response = new ErrorResponse(
                "Пользователь не обновлен", new Date()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotDeletedException.class})
    public ResponseEntity<ErrorResponse> handleException(NotDeletedException e) {
        ErrorResponse response = new ErrorResponse(
                "Удаляемого пользователя нет в списке", new Date()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}

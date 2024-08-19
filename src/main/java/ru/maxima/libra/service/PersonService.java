package ru.maxima.libra.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maxima.libra.dto.PersonDTO;
import ru.maxima.libra.exceptions.NotFoundException;
import ru.maxima.libra.models.Person;
import ru.maxima.libra.repositories.PersonRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonService(PersonRepository personRepository, ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
    }


    @Transactional
    public List<Person> getAllPeople() {
        return personRepository.findAllByRemovedIsFalseOrderById();
    }

    @Transactional
    public Person getPerson(Long id) {
        Optional<Person> foundPerson = Optional.ofNullable(personRepository.findById(id).orElse(null));
        return foundPerson.orElseThrow(() -> new NotFoundException());
    }

    @Transactional
    public Person updatePerson(Long id, Person updatedPerson) {
        if (updatedPerson != null) {
            Person person = getPerson(id);
            if (person == null) {
                return null;
            }
            person.setName(updatedPerson.getName());
            person.setAge(updatedPerson.getAge());
            person.setEmail(updatedPerson.getEmail());
            person.setPhoneNumber(updatedPerson.getPhoneNumber());
            person.setUpdatedAt(LocalDateTime.now());
            // person.setUpdatedPerson(getUserName());
            String password = updatedPerson.getPassword();
            // String encodedPassword = passwordEncoder.encode(password);
            // person.setPassword(encodedPassword);
            personRepository.save(person);
            return person;
        } else {
            return null;
        }
    }

    @Transactional
    public void savePerson(Person person) {
        personRepository.save(person);
    }


    @Transactional
    public void delete(Long id) {
        Person person = getPerson(id);
             //person.setRemovedPerson(getUserName());
            person.setRemovedAt(LocalDateTime.now());
            person.setRemoved(true);
            personRepository.save(person);
        }


    public PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

    public Person convertToPerson(PersonDTO personDTO) {
        Person person = modelMapper.map(personDTO, Person.class);
        personEnrich(person);
        return person;
    }

    private void personEnrich(Person p) {
        p.setCreatedAt(LocalDateTime.now());
        p.setCreatedPerson("ADMIN");
        p.setRole("USER");
        //  p.setPassword(passwordEncoder.encode(p.getPassword()));
        p.setRemoved(false);
    }
}

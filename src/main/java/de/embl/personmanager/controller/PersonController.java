package de.embl.personmanager.controller;

import de.embl.personmanager.model.Person;
import de.embl.personmanager.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(consumes = MediaType.ALL_VALUE)
    public List<Person> getAllPersons() {
        return personService.findAllPersons();
    }

    @PostMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity createPerson(@RequestBody Person person) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.createPerson(person));
    }

    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Person> getPersonById(@PathVariable(value = "id") Long personId) {
        Optional<Person> personOptional = personService.findPersonById(personId);
        return personOptional.map(person -> ResponseEntity.ok(personOptional.get())).orElseGet(() -> ResponseEntity.notFound().build());
//        if (!personOptional.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(personOptional.get());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Person> updatePerson(@PathVariable(value = "id") Long personId, @Valid @RequestBody Person personEntity) {
        Optional<Person> personOptional = Optional.ofNullable(personService.updatePerson(personId, personEntity));
        return personOptional.map(person -> ResponseEntity.accepted().body(person)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity delete(@PathVariable Long id) {
        personService.deletePersonById(id);
        return ResponseEntity.accepted().build();
    }
}

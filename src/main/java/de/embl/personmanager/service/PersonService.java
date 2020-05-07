package de.embl.personmanager.service;

import de.embl.personmanager.controller.PersonController;
import de.embl.personmanager.model.Person;
import de.embl.personmanager.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonRepository personRepository;

    public List<Person> findAllPersons() {
        return personRepository.findAll();
    }

    public Optional<Person> findPersonById(Long personId) {
        Optional<Person> personOptional = personRepository.findById(personId);
        if (!personOptional.isPresent()) {
            logger.error("PersonId " + personId + " is not existed");
        }
        return personOptional;
    }

    public Person createPerson(Person person) {
        Person personre = personRepository.save(person);
        return personre;
//        return personRepository.save(person);
    }

    public Person updatePerson(Long personId, Person personEntity) {
        Optional<Person> personOptional = personRepository.findById(personId);
        if (!personOptional.isPresent()) {
            logger.error("PersonId " + personId + " is not existed");
            return null;
        }

        Person person = personOptional.get();
        if (!StringUtils.isEmpty(personEntity.getFirstName())) person.setFirstName(personEntity.getFirstName());
        if (!StringUtils.isEmpty(personEntity.getLastName())) person.setLastName(personEntity.getLastName());
        person.setAge(personEntity.getAge());
        person.setFavouriteColour(personEntity.getFavouriteColour());
        person.setHobby(personEntity.getHobby());
        return personRepository.save(person);
    }

    public void deletePersonById(Long id) {
        personRepository.deleteById(id);
    }
}

package de.embl.personmanager.unittest;

import de.embl.personmanager.PersonApplication;
import de.embl.personmanager.controller.PersonController;
import de.embl.personmanager.model.Person;
import de.embl.personmanager.service.PersonService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest(classes = PersonApplication.class)
public class PersonControllerUnitTest {
    @InjectMocks
    PersonController personController;
    @Mock
    PersonService personService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreatePerson() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(personService.createPerson(any(Person.class))).thenReturn(any(Person.class));
        Person person = new Person("Jhon", "Seana", 30, "RED", Arrays.asList("Chess", "FootBall"));
        ResponseEntity responseEntity = personController.createPerson(person);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getStatusCode().name()).isEqualTo("CREATED");
    }

    @Test
    public void testGetAllPersons() {
        Person person1 = new Person("Jhon", "Seana", 30, "RED", Arrays.asList("Shopping", "FootBall"));
        Person person2 = new Person("Jhon", "Doe", 32, "BLACK", Arrays.asList("Chess"));

        personController.createPerson(person1);
        personController.createPerson(person2);

        List<Person> personList = Arrays.asList(person1, person2);
        when(personService.findAllPersons()).thenReturn(personList);

        List<Person> result = personController.getAllPersons();

        assertThat(result.size()).isEqualTo(2);

        assertThat(result.get(0).getFirstName())
                .isEqualTo(person1.getFirstName());

        assertThat(result.get(1).getFirstName())
                .isEqualTo(person2.getFirstName());
    }

    @Test
    public void testGetPersonById() {
        Person person1 = new Person("Jhon", "Seana", 30, "RED", Arrays.asList("Chess", "FootBall"));
        personController.createPerson(person1);
//        when(personService.findPersonById(1L)).thenReturn(Optional.of(person1));
        assertNotNull(personService.findPersonById(1L));
    }

    @Test
    public void testUpdatePersonById() {
        Person person = new Person("Jhon", "Seana", 30, "RED", Arrays.asList("Chess", "FootBall"));
        ResponseEntity r = personController.createPerson(person);
        person.setLastName("Doe");
        ResponseEntity<Person> responseEntity = personController.updatePerson(1L, person);

//       assertThat(responseEntity.getStatusCodeValue()).isEqualTo(202);
       assertThat(responseEntity.getStatusCode().name()).isEqualTo("UPDATED");
    }

    @Test
    public void testDeletePersonById() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Person person = new Person("Jhon", "Seana", 30, "RED", Arrays.asList("Chess", "FootBall"));
        personController.createPerson(person);
        ResponseEntity responseEntity = personController.delete(1L);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(202);
        assertThat(responseEntity.getStatusCode().name()).isEqualTo("ACCEPTED");
    }
}
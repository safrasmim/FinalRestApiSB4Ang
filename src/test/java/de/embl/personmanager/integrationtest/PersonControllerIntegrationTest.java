package de.embl.personmanager.integrationtest;

import de.embl.personmanager.PersonApplication;
import de.embl.personmanager.model.Person;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersonApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void testAContextLoads() {
    }

    @Test
    public void testAGetAllPersons() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/person",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testBCreatePerson() {
        Person person = new Person();
        person.setFirstName("Jon");
        person.setLastName("Doe");
        person.setAge(25);
        person.setFavouriteColour("Red");
        person.setHobby(Arrays.asList("Chess", "FootBall"));

        ResponseEntity<Person> postResponse = restTemplate.postForEntity(getRootUrl() + "/person", person, Person.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testCGetPersonById() {
        int id = 1;
        Person person = restTemplate.getForObject(getRootUrl() + "/person/" + id, Person.class);
        System.out.println(person.getFirstName());
        assertNotNull(person);
    }

    @Test
    public void testDUpdatePerson() {
        int id = 1;
        Person person = restTemplate.getForObject(getRootUrl() + "/person/" + id, Person.class);
        person.setFirstName("Jon");
        person.setLastName("Doe");
        restTemplate.put(getRootUrl() + "/person/" + id, person);
        Person updatedPerson = restTemplate.getForObject(getRootUrl() + "/person/" + id, Person.class);
        assertNotNull(updatedPerson);
    }

    @Test
    public void testEDeletePerson() {
        int id = 1;
        Person person = restTemplate.getForObject(getRootUrl() + "/person/" + id, Person.class);
        assertNotNull(person);
        restTemplate.delete(getRootUrl() + "/person/" + id);
        try {
            person = restTemplate.getForObject(getRootUrl() + "/person/" + id, Person.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}
package de.embl.personmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import java.util.List;

@JsonPropertyOrder({ "first_name", "last_name", "age", "favourite_colour", "hobby" })
@Entity
@Table(name = "PERSON")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @JsonIgnore
    private long id;
    @JsonProperty("first_name")
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @JsonProperty("last_name")
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "age", nullable = false)
    private int age;
    @JsonProperty("favourite_colour")
    @Column(name = "favourite_colour", nullable = false)
    private String favouriteColour;
    @ElementCollection
    @CollectionTable(name="HOBBY", joinColumns=@JoinColumn(name="person_id"))
    @Column(name="hobby")
    private List<String> hobby;

    public Person() {
    }

    public Person(String firstName, String lastName, int age, String favouriteColour, List<String> hobby) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.favouriteColour = favouriteColour;
        this.hobby = hobby;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFavouriteColour() {
        return favouriteColour;
    }

    public void setFavouriteColour(String favouriteColour) {
        this.favouriteColour = favouriteColour;
    }

    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", age=").append(age);
        sb.append(", favouriteColour='").append(favouriteColour).append('\'');
        sb.append(", hobby=").append(hobby);
        sb.append('}');
        return sb.toString();
    }
}
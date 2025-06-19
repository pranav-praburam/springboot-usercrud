package com.example.userCrud;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity //@Entity: Marks this class as a JPA entity (it will become a table in the database).
@Table(name = "users")
public class User {

    @Id //Primary key for the table.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Tells the DB to auto-generate the ID when a new user is added.
    private Long id;

    //the columns in your database table: name, email, age.
    private String name;
    private String email;
    private int age;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    /*
     * @OneToMany: Says that one User can have many Posts.
     * mappedBy = "user": This links it to the user field inside the Post class (the one we already defined with @ManyToOne).
     * cascade = CascadeType.ALL: So that when you save/delete a user, their posts get handled automatically.
     * orphanRemoval = true: Deletes posts that no longer belong to a user.
     * new ArrayList<>(): Initializes the list so it’s not null.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

}

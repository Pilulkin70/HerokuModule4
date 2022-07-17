package com.heroku.module3.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String firstName;
    private String lastName;
    private int age;

    @OneToOne
    private Subject subject;

    @Override
    public String toString() {
        return String.format("Lecturer%n First name: '%s'%n Last name: '%s'%n Age: %d%n",
                this.firstName, this.lastName, this.age);
    }
}

package com.example.task1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String corpName;

    private String directorName;

    @OneToOne
    private Address address;

    public Company(String corpName, String directorName, Address address) {
        this.corpName = corpName;
        this.directorName = directorName;
        this.address = address;
    }
}

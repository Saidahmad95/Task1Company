package com.example.task1.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true)
    private String phoneNumber;

    @OneToOne
    private Address address;

    @ManyToOne
    private Department department;

    public Worker(String name, String phoneNumber, Address address, Department department) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.department = department;
    }
}

package com.example.task1.repository;

import com.example.task1.entity.Address;
import com.example.task1.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    boolean existsByStreetAndHomeNumber(String street, String homeNumber);
    boolean existsByStreetAndHomeNumberAndIdNot(String street, String homeNumber, Integer id);
}

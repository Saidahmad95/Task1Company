package com.example.task1.repository;

import com.example.task1.entity.Address;
import com.example.task1.entity.Company;
import com.example.task1.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    boolean existsByDepartNameAndCompany(String departName, Company company);
//    boolean existsByDepartNameAndCompanyAndIdNot(String departName, Company company, Integer id);

}

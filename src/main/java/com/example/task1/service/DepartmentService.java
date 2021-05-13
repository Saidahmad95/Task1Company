package com.example.task1.service;

import com.example.task1.dto.DepartmentDTO;
import com.example.task1.dto.ResponseDTO;
import com.example.task1.entity.Company;
import com.example.task1.entity.Department;
import com.example.task1.repository.CompanyRepository;
import com.example.task1.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    CompanyRepository companyRepository;


    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public ResponseDTO getDepartmentById(Integer id) {
        Optional<Department> byId = departmentRepository.findById(id);
        return byId.map(department -> new ResponseDTO("Found !", true, department)).orElseGet(() -> new ResponseDTO("Not Found !", false, new Department()));
    }

    public ResponseDTO addDepartment(DepartmentDTO departmentDTO) {
        Optional<Company> companyOptional = companyRepository.findById(departmentDTO.getCompanyID());
        if (!companyOptional.isPresent())
            return new ResponseDTO("Company with id: '" + departmentDTO.getCompanyID() + "' not found", false, new Department());

        if (departmentRepository.existsByDepartNameAndCompany(departmentDTO.getDepartName(), companyOptional.get()))
            return new ResponseDTO("Company: '" + companyOptional.get().getCorpName() + "' already have department with name: ' " + departmentDTO.getDepartName() + "'!", false, new Department());

        Department newDepartment = new Department(departmentDTO.getDepartName(), companyOptional.get());
        Department save = departmentRepository.save(newDepartment);
        return new ResponseDTO("Department with name: '" + save.getDepartName() + "'successfully added to company '" + save.getCompany().getCorpName() + "' !", true, save);
    }

    public ResponseDTO editDepartment(Integer id, DepartmentDTO departmentDTO) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if (!departmentOptional.isPresent())
            return new ResponseDTO("Department with id: '" + id + "' not found !", false);

        Optional<Company> companyOptional = companyRepository.findById(departmentDTO.getCompanyID());
        if (!companyOptional.isPresent())
            return new ResponseDTO("Company with id: '" + departmentDTO.getCompanyID() + "' not found !", false);

        if (departmentRepository.existsByDepartNameAndCompany(departmentDTO.getDepartName(), companyOptional.get()))
            return new ResponseDTO("Company: '" + companyOptional.get().getCorpName() + "' already have department with name :' " + departmentDTO.getDepartName() + "' !", false);

        Department editDepartment = departmentOptional.get();
        editDepartment.setDepartName(departmentDTO.getDepartName());
        editDepartment.setCompany(companyOptional.get());
        Department save = departmentRepository.save(editDepartment);
        return new ResponseDTO("Department with id: '" + id + "' successfully edited !", true);
    }

    public ResponseDTO deleteDepartment(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isPresent()){
            departmentRepository.deleteById(id);
            return new ResponseDTO("Department with id:'"+id+"' successfully deleted !",true);
        }
        return new ResponseDTO("Department with id:'"+id+"' not found !",false);
    }
}

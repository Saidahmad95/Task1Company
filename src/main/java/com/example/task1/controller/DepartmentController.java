package com.example.task1.controller;

import com.example.task1.dto.DepartmentDTO;
import com.example.task1.dto.ResponseDTO;
import com.example.task1.entity.Department;
import com.example.task1.repository.CompanyRepository;
import com.example.task1.repository.DepartmentRepository;
import com.example.task1.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/department")
public class DepartmentController {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departmentList = departmentService.getAllDepartments();
        return ResponseEntity.ok(departmentList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable Integer id) {
        ResponseDTO responseDTO = departmentService.getDepartmentById(id);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND).body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<?> addDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
        ResponseDTO responseDTO = departmentService.addDepartment(departmentDTO);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(responseDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> editDepartment(@PathVariable Integer id, @Valid @RequestBody DepartmentDTO departmentDTO) {
        ResponseDTO responseDTO = departmentService.editDepartment(id, departmentDTO);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(responseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Integer id) {
        ResponseDTO responseDTO = departmentService.deleteDepartment(id);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(responseDTO);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}

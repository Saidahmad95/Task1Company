package com.example.task1.controller;

import com.example.task1.dto.CompanyDTO;
import com.example.task1.dto.ResponseDTO;
import com.example.task1.entity.Company;
import com.example.task1.repository.CompanyRepository;
import com.example.task1.service.CompanyService;
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
@RequestMapping(value = "/api/company")
public class CompanyController {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CompanyService companyService;

    @GetMapping
    public ResponseEntity<?> getAllCompanies() {
        List<Company> allCompanies = companyService.getAllCompanies();
        return ResponseEntity.ok(allCompanies);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCompany(@PathVariable Integer id) {
        ResponseDTO responseDTO = companyService.getCompany(id);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND).body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<?> addCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        ResponseDTO responseDTO = companyService.addCompany(companyDTO);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(responseDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> editCompany(@PathVariable Integer id, @Valid @RequestBody CompanyDTO companyDTO) {
        ResponseDTO responseDTO = companyService.editCompany(id, companyDTO);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(responseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Integer id) {
        ResponseDTO responseDTO = companyService.deleteCompany(id);
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

package com.example.task1.service;

import com.example.task1.dto.CompanyDTO;
import com.example.task1.dto.ResponseDTO;
import com.example.task1.entity.Address;
import com.example.task1.entity.Company;
import com.example.task1.repository.AddressRepository;
import com.example.task1.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    AddressRepository addressRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public ResponseDTO getCompany(Integer id) {
        Optional<Company> byId = companyRepository.findById(id);
        return byId.map(company -> new ResponseDTO("Found !", true, company)).orElseGet(() -> new ResponseDTO("Not found !", false,new Company()));
    }

    public ResponseDTO addCompany(CompanyDTO companyDTO) {
        if (companyRepository.existsByCorpName(companyDTO.getCorpName()))
            return new ResponseDTO("Company with name: '" + companyDTO.getCorpName() + "' already exists !", false);

        if (addressRepository.existsByStreetAndHomeNumber(companyDTO.getStreet(), companyDTO.getHomeNumber()))
            return new ResponseDTO("Address with street: '" + companyDTO.getStreet() + "' and home-number: '" + companyDTO.getHomeNumber() + "' already exists !", false);

        Address newAddress = new Address(companyDTO.getStreet(), companyDTO.getHomeNumber());
        Company newCompany = new Company(companyDTO.getCorpName(), companyDTO.getDirectorName(), newAddress);

        addressRepository.save(newAddress);
        companyRepository.save(newCompany);

        return new ResponseDTO("Company with name: '" + companyDTO.getCorpName() + "' successfully added !", true);

    }

    public ResponseDTO editCompany(Integer id, CompanyDTO companyDTO) {
        Optional<Company> byId = companyRepository.findById(id);
        if (!byId.isPresent())
            return new ResponseDTO("Company with id: '" + id + "' not found!", false);

        Company editCompany = byId.get();
        if (companyRepository.existsByCorpNameAndIdNot(companyDTO.getCorpName(), id))
            return new ResponseDTO("Company with name: '" + companyDTO.getCorpName() + "' already exists!", false);

        if (addressRepository.existsByStreetAndHomeNumberAndIdNot(companyDTO.getStreet(), companyDTO.getHomeNumber(), editCompany.getAddress().getId()))
            return new ResponseDTO("Address with street: '" + companyDTO.getStreet() + "' and home-number: '" + companyDTO.getHomeNumber() + "' already exists !", false);

        editCompany.setCorpName(companyDTO.getCorpName());
        editCompany.setDirectorName(companyDTO.getDirectorName());

        Address editCompanyAddress = editCompany.getAddress();
        editCompanyAddress.setStreet(companyDTO.getStreet());
        editCompanyAddress.setHomeNumber(companyDTO.getHomeNumber());

        addressRepository.save(editCompanyAddress);
        companyRepository.save(editCompany);

        return new ResponseDTO("Company id: '" + id + "' successfully edited !", true);
    }

    public ResponseDTO deleteCompany(Integer id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return new ResponseDTO("Company with id:'" + id + "' successfully deleted !", true);
        }
        return new ResponseDTO("Company with id:'" + id + "' not found !", false);
    }
}

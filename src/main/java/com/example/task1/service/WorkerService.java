package com.example.task1.service;

import com.example.task1.dto.ResponseDTO;
import com.example.task1.dto.WorkerDTO;
import com.example.task1.entity.Address;
import com.example.task1.entity.Department;
import com.example.task1.entity.Worker;
import com.example.task1.repository.AddressRepository;
import com.example.task1.repository.DepartmentRepository;
import com.example.task1.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public ResponseDTO getWorkerById(Integer id) {
        Optional<Worker> workerOptional = workerRepository.findById(id);
        return workerOptional.map(worker -> new ResponseDTO("Found !", true, worker)).orElseGet(() -> new ResponseDTO("Not found !", false));
    }

    public ResponseDTO addWorker(WorkerDTO workerDTO) {
        if (workerRepository.existsByPhoneNumber(workerDTO.getPhoneNumber()))
            return new ResponseDTO("Worker with phone-number: '" + workerDTO.getPhoneNumber() + "' already exists !", false);

        Optional<Department> departmentOptional = departmentRepository.findById(workerDTO.getDepartmentID());
        if (!departmentOptional.isPresent())
            return new ResponseDTO("Department with id:'" + workerDTO.getDepartmentID() + "' not found !", false);

        if (addressRepository.existsByStreetAndHomeNumber(workerDTO.getStreet(), workerDTO.getHomeNumber()))
            return new ResponseDTO("Address with street: '" + workerDTO.getStreet() + "' and home-number'" + workerDTO.getHomeNumber() + "' already exists !", false);

        Address newAddress = new Address(workerDTO.getStreet(), workerDTO.getHomeNumber());
        Address saveAddress = addressRepository.save(newAddress);

        Worker newWorker = new Worker(workerDTO.getName(), workerDTO.getPhoneNumber(), saveAddress, departmentOptional.get());
        Worker saveWorker = workerRepository.save(newWorker);

        return new ResponseDTO("Worker with name: '" + saveWorker.getName() + "' successfully added !", true, saveWorker);
    }

    public ResponseDTO editWorker(Integer id, WorkerDTO workerDTO) {
        Optional<Worker> workerOptional = workerRepository.findById(id);
        if (!workerOptional.isPresent())
            return new ResponseDTO("Worker with id:'" + id + "' not found !", false);

        if (workerRepository.existsByPhoneNumberAndIdNot(workerDTO.getPhoneNumber(), id))
            return new ResponseDTO("Worker with phone-number: '" + workerDTO.getPhoneNumber() + "' already exists !", false);

        if (addressRepository.existsByStreetAndHomeNumberAndIdNot(workerDTO.getStreet(), workerDTO.getHomeNumber(), id))
            return new ResponseDTO("Address with street: '" + workerDTO.getStreet() + "' and home-number: '" + workerDTO.getHomeNumber() + "' already exists !", false);

        Optional<Department> departmentOptional = departmentRepository.findById(workerDTO.getDepartmentID());
        if (!departmentOptional.isPresent())
            return new ResponseDTO("Department with id: '" + workerDTO.getDepartmentID() + "' not found !", false);

        Worker editWorker = workerOptional.get();
        Address editWorkerAddress = editWorker.getAddress();

        editWorkerAddress.setStreet(workerDTO.getStreet());
        editWorkerAddress.setHomeNumber(workerDTO.getHomeNumber());
        addressRepository.save(editWorkerAddress);

        editWorker.setName(workerDTO.getName());
        editWorker.setPhoneNumber(workerDTO.getPhoneNumber());
        editWorker.setAddress(editWorkerAddress);
        editWorker.setDepartment(departmentOptional.get());
        Worker save = workerRepository.save(editWorker);

        return new ResponseDTO("Worker with id: '" + id + "' successfully edited !", true, save);

    }

    public ResponseDTO deleteWorker(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isPresent()) {
            workerRepository.deleteById(id);
            return new ResponseDTO("Worker with id:'" + id + "' successfully deleted !", true);
        }
        return new ResponseDTO("Worker with id:'" + id + "' not found !", false);
    }
}

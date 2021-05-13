package com.example.task1.controller;

import com.example.task1.dto.ResponseDTO;
import com.example.task1.dto.WorkerDTO;
import com.example.task1.entity.Worker;
import com.example.task1.repository.WorkerRepository;
import com.example.task1.service.WorkerService;
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
@RequestMapping(value = "/api/worker")
public class WorkerController {
    @Autowired
    WorkerService workerService;
    @Autowired
    WorkerRepository workerRepository;

    @GetMapping
    public ResponseEntity<List<Worker>> getAllWorkers() {
        List<Worker> workerList = workerService.getAllWorkers();
        return ResponseEntity.ok(workerList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getWorkerById(@PathVariable Integer id) {
        ResponseDTO responseDTO = workerService.getWorkerById(id);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND).body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<?> addWorker(@Valid @RequestBody WorkerDTO workerDTO) {
        ResponseDTO responseDTO = workerService.addWorker(workerDTO);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(responseDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> editWorker(@PathVariable Integer id, @Valid @RequestBody WorkerDTO workerDTO) {
        ResponseDTO responseDTO = workerService.editWorker(id, workerDTO);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(responseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteWorker(@PathVariable Integer id) {
        ResponseDTO responseDTO = workerService.deleteWorker(id);
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

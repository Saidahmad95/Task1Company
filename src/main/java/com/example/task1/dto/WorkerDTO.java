package com.example.task1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDTO {
    @NotNull(message = "Worker name must not be empty !")
    private String name;

    @NotNull(message = "Worker phone-number name must not be empty !")
    private String phoneNumber;

    @NotNull(message = "Street must not be empty !")
    private String street;

    @NotNull(message = "Home number must not be empty !")
    private String homeNumber;

    @NotNull(message = "Department must not be empty!")
    private Integer departmentID;
}

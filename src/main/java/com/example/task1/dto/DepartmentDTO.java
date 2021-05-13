package com.example.task1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
    @NotNull(message = "Department name must not be empty !")
    private String departName;

    @NotNull(message = "Company id must not be empty !")
    private Integer companyID;
}

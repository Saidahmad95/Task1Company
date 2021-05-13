package com.example.task1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {
    @NotNull(message = "Company name must not be empty !")
    private String corpName;

    @NotNull(message = "Director name must not be empty !")
    private String directorName;

    @NotNull(message = "Street must not be empty !")
    private String street;

    @NotNull(message = "Home number must not be empty !")
    private String homeNumber;
}

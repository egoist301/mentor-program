package com.epam.esm.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class OrderRequestDto {
    @NotEmpty(message = "doctors can't be empty or null")
    @JsonProperty("doctors")
    private Set<Long> doctorRequestDtos;

    public Set<Long> getDoctorRequestDtos() {
        return doctorRequestDtos;
    }

    public void setDoctorRequestDtos(Set<Long> doctorRequestDtos) {
        this.doctorRequestDtos = doctorRequestDtos;
    }
}

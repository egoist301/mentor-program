package com.epam.esm.dto.order;

import com.epam.esm.dto.doctor.DoctorResponseDto;
import com.epam.esm.dto.user.UserResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Set;

public class OrderResponseDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("doctors")
    private Set<DoctorResponseDto> doctorResponseDtos;
    @JsonProperty("create_date")
    private LocalDate createDate;

    public OrderResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<DoctorResponseDto> getDoctorResponseDtos() {
        return doctorResponseDtos;
    }

    public void setDoctorResponseDtos(Set<DoctorResponseDto> doctorResponseDtos) {
        this.doctorResponseDtos = doctorResponseDtos;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }
}

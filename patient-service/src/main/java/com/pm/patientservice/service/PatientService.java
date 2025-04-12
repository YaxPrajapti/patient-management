package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PatientService {
    public List<PatientResponseDto> getPatients();
    public PatientResponseDto createPatient(PatientRequestDto patientRequestDto);
}

package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PatientService {
    public List<PatientResponseDto> getPatients();
}

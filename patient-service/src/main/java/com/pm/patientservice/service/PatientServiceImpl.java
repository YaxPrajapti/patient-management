package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.exception.EmailAlreadyExistException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<PatientResponseDto> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream()
                .map(PatientMapper::toPatientResponseDto)
                .toList();
    }

    @Override
    public PatientResponseDto createPatient(PatientRequestDto patientRequestDto) {
        if (patientRepository.existsByEmail(patientRequestDto.getEmail())) {
            throw new EmailAlreadyExistException("Patient with this email already exists. " + patientRequestDto.getEmail());
        }
        Patient newPatient = patientRepository.save(
                PatientMapper.toPatientModel(patientRequestDto)
        );
        return PatientMapper.toPatientResponseDto(newPatient);
    }

    @Override
    public PatientResponseDto updatePatient(PatientRequestDto patientRequestDto, UUID patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: "+ patientId));
        if (patientRepository.existsByEmailAndIdNot(patientRequestDto.getEmail(), patientId)) {
            throw new EmailAlreadyExistException("Patient with this email already exists. " + patientRequestDto.getEmail());
        }
        patient.setName(patientRequestDto.getName());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setAddress(patientRequestDto.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toPatientResponseDto(updatedPatient);
    }

    @Override
    public void deletePatient(UUID patientId) {
        patientRepository.deleteById(patientId);
    }
}

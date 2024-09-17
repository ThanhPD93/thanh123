package mockProject.team3.Vaccination_20.service.impl;

import mockProject.team3.Vaccination_20.dto.forvaccine.VaccineDto;
import mockProject.team3.Vaccination_20.model.Vaccine;
import mockProject.team3.Vaccination_20.model.VaccineType;
import mockProject.team3.Vaccination_20.repository.VaccineRepository;
import mockProject.team3.Vaccination_20.repository.VaccineTypeRepository;
import mockProject.team3.Vaccination_20.model.Vaccine;
import mockProject.team3.Vaccination_20.repository.VaccineRepository;
import mockProject.team3.Vaccination_20.repository.VaccineTypeRepository;
import mockProject.team3.Vaccination_20.service.VaccineService;
import mockProject.team3.Vaccination_20.utils.Status;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import java.util.List;

@Service
public class VaccineServiceImpl implements VaccineService {
    @Autowired
    private VaccineRepository vaccineRepository;

    @Autowired
    private VaccineTypeRepository vaccineTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public VaccineDto createVaccine(VaccineDto vaccineDto) {
        Vaccine vaccine = modelMapper.map(vaccineDto, Vaccine.class);
        vaccine = vaccineRepository.save(vaccine);
        return modelMapper.map(vaccine, VaccineDto.class);
    }

    @Override
    public Page<VaccineDto> getVaccineList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return vaccineRepository.findAll(pageable)
                .map(vaccine -> modelMapper.map(vaccine, VaccineDto.class));
    }

    @Override
    public Page<VaccineDto> getVaccineListBySearchInput(String searchInput, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return vaccineRepository.findBySearchInput(searchInput, pageable)
                .map(vaccine -> modelMapper.map(vaccine, VaccineDto.class));
    }

    @Override
    public VaccineDto getVaccineById(String vaccineId) {
        Vaccine vaccine = vaccineRepository.findById(vaccineId)
                .orElseThrow(() -> new RuntimeException("Vaccine not found"));
        return modelMapper.map(vaccine, VaccineDto.class);
    }

    @Override
    public VaccineDto updateVaccine(String vaccineId, VaccineDto vaccineDto) {
        Vaccine existingVaccine = vaccineRepository.findById(vaccineId)
                .orElseThrow(() -> new RuntimeException("Vaccine not found"));
        modelMapper.map(vaccineDto, existingVaccine);
        existingVaccine = vaccineRepository.save(existingVaccine);
        return modelMapper.map(existingVaccine, VaccineDto.class);
    }

    @Override
    public void changeStatusVaccine(List<String> vaccineIds) {
        if (vaccineIds == null || vaccineIds.isEmpty()){
            throw new IllegalArgumentException("No vaccines selected for deletion");
        }
        for (String vaccineId : vaccineIds){
            Vaccine selectedVaccine = vaccineRepository.findById(vaccineId).get();
            selectedVaccine.setVaccineId(selectedVaccine.getVaccineId());
            selectedVaccine.setVaccineType(selectedVaccine.getVaccineType());
            selectedVaccine.setVaccineOrigin(selectedVaccine.getVaccineOrigin());
            selectedVaccine.setVaccineName(selectedVaccine.getVaccineName());
            selectedVaccine.setVaccineUsage(selectedVaccine.getVaccineUsage());
            selectedVaccine.setTimeEndNextInjection(selectedVaccine.getTimeEndNextInjection());
            selectedVaccine.setTimeBeginNextInjection(selectedVaccine.getTimeBeginNextInjection());
            selectedVaccine.setIndication(selectedVaccine.getIndication());
            selectedVaccine.setContraindication(selectedVaccine.getContraindication());
            selectedVaccine.setNumberOfInjection(selectedVaccine.getNumberOfInjection());
            selectedVaccine.setInjectionResults(selectedVaccine.getInjectionResults());
            selectedVaccine.setInjectionSchedule(selectedVaccine.getInjectionSchedule());
            selectedVaccine.setVaccineStatus(Status.INACTIVE);
            vaccineRepository.save(selectedVaccine);
        }
    }

    public List<String> importVaccineFromExcel(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        List<Vaccine> vaccines = new ArrayList<>();
        List<String> notifications = new ArrayList<>(); // List to store notifications

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            if (row == null) {
                continue; // Skip empty rows
            }

            Vaccine vaccine = new Vaccine();

            if (row.getCell(0) != null) {
                vaccine.setVaccineId(row.getCell(0).getStringCellValue());
            }
            if (row.getCell(1) != null) {
                vaccine.setContraindication(row.getCell(1).getStringCellValue());
            }
            if (row.getCell(2) != null) {
                vaccine.setIndication(row.getCell(2).getStringCellValue());
            }
            if (row.getCell(3) != null) {
                vaccine.setNumberOfInjection((int) row.getCell(3).getNumericCellValue());
            }
            if (row.getCell(4) != null) {
                vaccine.setVaccineOrigin(row.getCell(4).getStringCellValue());
            }
            if (row.getCell(5) != null) {
                vaccine.setTimeBeginNextInjection(row.getCell(5).getLocalDateTimeCellValue().toLocalDate());
            }
            if (row.getCell(6) != null) {
                vaccine.setTimeEndNextInjection(row.getCell(6).getLocalDateTimeCellValue().toLocalDate());
            }
            if (row.getCell(7) != null) {
                vaccine.setVaccineUsage(row.getCell(7).getStringCellValue());
            }
            if (row.getCell(8) != null) {
                vaccine.setVaccineName(row.getCell(8).getStringCellValue());
            }
            if (row.getCell(9) != null) {
                vaccine.setVaccineStatus(Status.valueOf(row.getCell(9).getStringCellValue()));
            }

            if (row.getCell(10) != null) {
                String vaccineTypeId = row.getCell(10).getStringCellValue();
                VaccineType vaccineType = vaccineTypeRepository.findById(vaccineTypeId).orElse(null);
                if (vaccineType != null) {
                    if (vaccineType.getVaccineTypeStatus() == Status.ACTIVE) {
                        vaccine.setVaccineType(vaccineType);
                        vaccines.add(vaccine); // Add to vaccines list
                    } else {
                        // Add a notification if vaccineTypeStatus is INACTIVE
                        notifications.add("Cannot add vaccine at row " + (i + 1) + " because the vaccine type is inactive.");
                    }
                }
            }
        }

        // Save all valid vaccines
        vaccineRepository.saveAll(vaccines);

        workbook.close();
        return notifications; // Return notifications to the controller
    }

    public List<Vaccine> getVaccinesByType(String vaccineTypeId) {
        if (vaccineTypeId != null) {
            return vaccineRepository.findByVaccineType_VaccineTypeId(vaccineTypeId);
        }
        return vaccineRepository.findAll();
    }

    @Override
    public Vaccine findByName(String vaccineName) {
        return null;
    }

    public List<Vaccine> getAllVaccines() {
        return vaccineRepository.findAll();
    }
}

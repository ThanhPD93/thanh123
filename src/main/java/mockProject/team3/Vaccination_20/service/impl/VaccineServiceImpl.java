package mockProject.team3.Vaccination_20.service.impl;

import mockProject.team3.Vaccination_20.dto.forvaccine.VaccineDto;
import mockProject.team3.Vaccination_20.model.Vaccine;
import mockProject.team3.Vaccination_20.model.VaccineType;
import mockProject.team3.Vaccination_20.repository.VaccineRepository;
import mockProject.team3.Vaccination_20.repository.VaccineTypeRepository;
import mockProject.team3.Vaccination_20.service.VaccineService;
import mockProject.team3.Vaccination_20.utils.VaccineStatus;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        vaccine.setStatus(VaccineStatus.ACTIVE);
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
            selectedVaccine.setStatus(VaccineStatus.INACTIVE);
            vaccineRepository.save(selectedVaccine);
        }
    }

    @Override
    public void importVaccineFromExcel(MultipartFile file) throws IOException {
        List<Vaccine> vaccineList = new ArrayList<>();
        try(InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                Vaccine vaccine = new Vaccine();

                // Convert numeric to string if necessary, and check if the cell is null
                vaccine.setVaccineId(getCellValueAsString(row.getCell(0)));
                vaccine.setVaccineUsage(getCellValueAsString(row.getCell(1)));
                vaccine.setContraindication(getCellValueAsString(row.getCell(2)));
                vaccine.setIndication(getCellValueAsString(row.getCell(3)));

                Cell numberOfInjectionCell = row.getCell(4);
                if (numberOfInjectionCell != null && numberOfInjectionCell.getCellType() == CellType.NUMERIC) {
                    vaccine.setNumberOfInjection((int) numberOfInjectionCell.getNumericCellValue());
                }

                // Map the status from Excel to the enum
//                String status = getCellValueAsString(row.getCell(5)).toUpperCase();
                vaccine.setStatus(VaccineStatus.ACTIVE);

                // Handle date parsing from Excel
                vaccine.setTimeBeginNextInjection(convertExcelDateToLocalDate(row.getCell(6)));
                vaccine.setTimeEndNextInjection(convertExcelDateToLocalDate(row.getCell(7)));

                vaccine.setVaccineName(getCellValueAsString(row.getCell(8)));
                vaccine.setVaccineOrigin(getCellValueAsString(row.getCell(9)));

                // Fetch the VaccineType by ID
                String vaccineTypeId = getCellValueAsString(row.getCell(10));
                VaccineType vaccineType = vaccineTypeRepository.findById(vaccineTypeId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Vaccine Type ID: " + vaccineTypeId));
                vaccine.setVaccineType(vaccineType);

                vaccineList.add(vaccine);
            }
        }
        vaccineRepository.saveAll(vaccineList);
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString(); // Handle date values if needed
                } else {
                    return String.valueOf((int) cell.getNumericCellValue()); // Convert numeric to string
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private LocalDate convertExcelDateToLocalDate(Cell cell) {
        if (cell == null) {
            return null; // Handle blank cells
        }
        if (DateUtil.isCellDateFormatted(cell)) {
            Date date = cell.getDateCellValue();
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        // If the cell is not a date, log or handle the case appropriately
        throw new IllegalArgumentException("Cell does not contain a valid date");
    }

}

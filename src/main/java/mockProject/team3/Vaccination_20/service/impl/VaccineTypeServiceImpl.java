package mockProject.team3.Vaccination_20.service.impl;

import mockProject.team3.Vaccination_20.model.Vaccine;
import mockProject.team3.Vaccination_20.model.VaccineType;
import mockProject.team3.Vaccination_20.repository.VaccineTypeRepository;
import mockProject.team3.Vaccination_20.service.VaccineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccineTypeServiceImpl implements VaccineTypeService {

	@Autowired
    private  VaccineTypeRepository vaccineTypeRepository;

    @Override
    public List<VaccineType> getAllVaccineTypes() {
        return vaccineTypeRepository.findAll();
    }
}

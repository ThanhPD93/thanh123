package mockProject.team3.Vaccination_20.service.impl;

import mockProject.team3.Vaccination_20.model.Vaccine;
import mockProject.team3.Vaccination_20.repository.VaccineRepository;
import mockProject.team3.Vaccination_20.repository.VaccineTypeRepository;
import mockProject.team3.Vaccination_20.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class VaccineServiceImpl implements VaccineService {

    @Autowired
    private VaccineRepository vaccineRepository;

    @Autowired
    private VaccineTypeRepository vaccineTypeRepository;

//    @Override
//    public List<Vaccine> getAllVaccines() {
//        return vaccineRepository.findAll();
//    }

    public List<Vaccine> getVaccinesByType(String vaccineTypeId) {
        if (vaccineTypeId != null) {
            return vaccineRepository.findByVaccineType_VaccineTypeId(vaccineTypeId);
        }
        return vaccineRepository.findAll();
    }

    public List<Vaccine> getAllVaccines() {
        return vaccineRepository.findAll();
    }

}

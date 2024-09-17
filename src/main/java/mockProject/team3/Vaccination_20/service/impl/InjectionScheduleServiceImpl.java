package mockProject.team3.Vaccination_20.service.impl;

import jakarta.annotation.PostConstruct;
import mockProject.team3.Vaccination_20.dto.injectionScheduleDto.InjectionScheduleDto;
import mockProject.team3.Vaccination_20.dto.injectionScheduleDto.SaveRequestInjectionSchedule;
import mockProject.team3.Vaccination_20.dto.injectionScheduleDto.VaccineDto;
import mockProject.team3.Vaccination_20.model.InjectionSchedule;
import mockProject.team3.Vaccination_20.model.Vaccine;
import mockProject.team3.Vaccination_20.repository.InjectionScheduleRepository;
import mockProject.team3.Vaccination_20.repository.VaccineRepository;
import mockProject.team3.Vaccination_20.service.InjectionScheduleService;
import mockProject.team3.Vaccination_20.utils.InjectionScheduleStatus;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InjectionScheduleServiceImpl implements InjectionScheduleService {
    @Autowired
    private InjectionScheduleRepository injectionScheduleRepository;

    @Autowired
    private VaccineRepository vaccineRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void setupModelMapper() {
        modelMapper.addMappings(new PropertyMap<Vaccine, VaccineDto>() {
            @Override
            protected void configure() {
                map().setVaccineName(source.getVaccineName());
            }
        });
    }

    @Override
    public List<InjectionScheduleDto> findAll() {
        List<InjectionSchedule> injectionSchedules = injectionScheduleRepository.findAll();
        List<InjectionScheduleDto> injectionScheduleDtos = modelMapper.map(
                injectionSchedules,
                new TypeToken<List<InjectionScheduleDto>>() {}.getType()
        );

        return injectionScheduleDtos;
    }

    @Override
    public int save(SaveRequestInjectionSchedule saveRequestInjectionSchedule) {
        InjectionSchedule injectionSchedule = modelMapper.map(saveRequestInjectionSchedule, InjectionSchedule.class);
        System.out.println(injectionSchedule.toString());
        LocalDate today = LocalDate.now();
        if(today.isBefore(injectionSchedule.getStartDate())){
            injectionSchedule.setStatus(InjectionScheduleStatus.NOT_YET);
        } else if (today.isAfter(injectionSchedule.getEndDate())) {
            injectionSchedule.setStatus(InjectionScheduleStatus.OVER);

        } else {
            injectionSchedule.setStatus(InjectionScheduleStatus.OPEN);
        }
        Vaccine vaccine = vaccineRepository.findByVaccineName(saveRequestInjectionSchedule.getVaccineName());
        if(vaccine == null){
            return -1;
        }
        injectionSchedule.setVaccineFromInjectionSchedule(vaccine);
        if (injectionScheduleRepository.save(injectionSchedule).getInjectionScheduleId() != null){
            return 1;
        }
        return 0;
    }
}

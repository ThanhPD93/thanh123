package mockProject.team3.Vaccination_20.repository;

import mockProject.team3.Vaccination_20.model.InjectionSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InjectionScheduleRepository extends JpaRepository<InjectionSchedule, String> {
    @Query("SELECT i FROM InjectionSchedule i " +
            "JOIN i.vaccineFromInjectionSchedule v " +
            "WHERE (LOWER(v.vaccineName) LIKE LOWER(CONCAT('%', :searchInput, '%')) " +
            "OR LOWER(i.injectionScheduleDescription) LIKE LOWER(CONCAT('%', :searchInput, '%')) " +
            "OR LOWER(i.place) LIKE LOWER(CONCAT('%', :searchInput, '%')))")
    Page<InjectionSchedule> findBySearch(String searchInput, Pageable pageable);

    InjectionSchedule findByInjectionScheduleId(String id);
}

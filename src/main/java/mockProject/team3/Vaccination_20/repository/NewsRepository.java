package mockProject.team3.Vaccination_20.repository;

import mockProject.team3.Vaccination_20.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, String> {
    @Query("SELECT n FROM News n WHERE " +
            "LOWER(n.title) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(n.content) LIKE LOWER(CONCAT('%', :searchInput, '%'))")
    Page<News> findBySearch(String searchInput, Pageable pageable);
}

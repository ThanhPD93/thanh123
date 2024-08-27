package mockProject.team3.Vaccination_20.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewsType {
	@Id
    @Column(length = 36)
    private String newsTypeId;

    @Column(length = 10)
    private String description;

    @Column(length = 50)
    private String newsTypeName;

    //relationship

    @OneToMany(mappedBy = "newsType")
    private List<News> news;
}

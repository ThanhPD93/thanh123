package mockProject.team3.Vaccination_20.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class News {
    @Id
    @Column(length = 36)
    private String newsId;

    @Column(length = 4000)
    private String content;

    @Column(length = 1000)
    private String preview;

    @Column(length = 300)
    private String title;

    @Column
    private LocalDate postDate;

    //relationship
    @ManyToOne
    private NewsType newsType;
}

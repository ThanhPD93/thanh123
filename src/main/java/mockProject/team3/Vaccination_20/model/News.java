package mockProject.team3.Vaccination_20.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class News {
    @Id
    @GeneratedValue(generator = "news_id")
    @GenericGenerator(name = "news_id", strategy = "mockProject.team3.Vaccination_20.utils.IdGenerator.NewsIdGenerator")
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

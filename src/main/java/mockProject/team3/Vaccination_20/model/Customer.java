package mockProject.team3.Vaccination_20.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockProject.team3.Vaccination_20.utils.Gender;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(generator = "customer_id")
    @GenericGenerator(name = "customer_id", strategy = "mockProject.team3.Vaccination_20.utils.IdGenerator.CustomerIdGenerator")
    @Column(length = 36)
    private String customerId;

    @Column(nullable = false)
	private String address;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String fullName;

    @Column(nullable = false)
    private Gender gender;

    @Column(length = 12, nullable = false)
    private String identityCard;

    @Column(nullable = false)
	private String password;

    @Column(length = 20, nullable = false)
    private String phone;

    @Column(nullable = false)
    private String username;

    //relationship
    @OneToMany(mappedBy = "customer")
    private List<InjectionResult> injectionResults;

}

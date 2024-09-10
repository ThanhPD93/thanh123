package mockProject.team3.Vaccination_20.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockProject.team3.Vaccination_20.utils.Gender;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    @Column(length = 36)
    private String customerId;

	private String address;

    private LocalDate dateOfBirth;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String fullName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 12)
    private String identityCard;

	private String password;

    @Column(length = 20)
    private String phone;

    private String username;

    //relationship
    @OneToMany(mappedBy = "customer")
    private List<InjectionResult> injectionResults;

}

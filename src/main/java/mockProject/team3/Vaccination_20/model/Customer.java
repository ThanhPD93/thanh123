package mockProject.team3.Vaccination_20.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

package mockProject.team3.Vaccination_20.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee implements UserDetails {
    @Id
    @Column(length = 36)
    private String EmployeeId;

    private String address;

    private LocalDate dateOfBirth;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String employeeName;

    private Gender gender;

    private String image;

    private String password;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String position;

    private String username;

    private String workingPlace;

    //override userdetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package notehospital.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    private String facility_name;
    private String address;
    private String phone;
    private String president;

    @OneToMany(mappedBy = "facilitysv", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Set<Service> services;

}

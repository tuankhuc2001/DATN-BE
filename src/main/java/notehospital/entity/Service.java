package notehospital.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @Lob
    private String image;
    private String name;
    private Double price;
    private String description;

    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Result> results;

    @OneToMany(mappedBy = "serviceac", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Account> accounts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facilityac_id")
    @JsonProperty("facility")
    private Facility facilitysv;

}

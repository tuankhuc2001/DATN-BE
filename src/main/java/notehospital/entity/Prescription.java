package notehospital.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import notehospital.entity.Order;
import notehospital.entity.PrescriptionItem;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    Order order;

    @OneToMany(mappedBy = "prescription",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Set<PrescriptionItem> prescriptionItems = new HashSet<>();
}

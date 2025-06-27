package notehospital.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PrescriptionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    private String times;
    private int quantity;

    private boolean isDeleted = false;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "prescription_id")
    Prescription prescription;

    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "medicine_id")
    Medicine medicine;
}

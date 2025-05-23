package notehospital.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

import lombok.*;
import notehospital.enums.ScheduleStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "schedule")
@Component
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date createdAt;
    private String note;
    private Date testDate;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus status = ScheduleStatus.CONFIRM;

    @ManyToOne()
    @JoinColumn(name = "account_id")
    private Account patient;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnore
    @JoinColumn(name = "doctor_id")
    private Account doctor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;


    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Result> results;

}

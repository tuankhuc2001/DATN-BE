package notehospital.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import notehospital.enums.ResultLevel;


import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    private String comment;
    private String value;

    @Enumerated(EnumType.STRING)
    private ResultLevel level;

    @ManyToOne()
    @JoinColumn(name = "order_id")
    @JsonIgnore
    Order order;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name =   "service_id")
    Service service;
}

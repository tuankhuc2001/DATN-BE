package notehospital.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import notehospital.entity.Facility;

import java.sql.Clob;

@Data
@Getter
@Setter
public class ServiceDTO {
    private long id;
    private String image;
    private String name;
    private Double price;
    private String description;
    private FacilityResponse facility;
}

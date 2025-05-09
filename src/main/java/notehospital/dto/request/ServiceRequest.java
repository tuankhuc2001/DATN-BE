package notehospital.dto.request;

import lombok.Data;

@Data
public class ServiceRequest {
    long id;
    String image;
    String name;
    String price;
    String description;
    private long facilityac_id;
}

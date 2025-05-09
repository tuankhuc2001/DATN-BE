package notehospital.dto.request;

import lombok.Data;

@Data

public class FacilityRequest {
    private String facility_name;
    private String address;
    private String phone;
    private String president;
}

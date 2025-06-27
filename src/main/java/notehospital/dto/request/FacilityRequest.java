package notehospital.dto.request;

import lombok.Data;

@Data

public class FacilityRequest {
    private String id;

    private String facility_name;
    private String phone;
    private String code;
    private String description;
}

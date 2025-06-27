package notehospital.Mapping;

import notehospital.dto.response.FacilityResponse;
import notehospital.entity.Facility;

public class FacilityMapping {
    public static FacilityResponse MapEntityToResponse(Facility facility) {

        FacilityResponse facilityResponse = new FacilityResponse();
        facilityResponse.setFacility_name(facility.getFacility_name());
        facilityResponse.setPhone(facility.getPhone());
        facilityResponse.setCode(facility.getCode());
        facilityResponse.setDescription(facility.getDescription());
        return facilityResponse;

    }
}

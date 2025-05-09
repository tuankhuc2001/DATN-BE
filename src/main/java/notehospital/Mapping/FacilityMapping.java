package notehospital.Mapping;

import notehospital.dto.response.FacilityResponse;
import notehospital.entity.Facility;

public class FacilityMapping {
    public static FacilityResponse MapEntityToResponse(Facility facility) {

        FacilityResponse facilityResponse = new FacilityResponse();
        facilityResponse.setFacility_name(facility.getFacility_name());
        facilityResponse.setAddress(facility.getAddress());
        facilityResponse.setPhone(facility.getPhone());
        facilityResponse.setPresident(facility.getPresident());
        return facilityResponse;

    }
}

package notehospital.Mapping;

import notehospital.dto.response.ServiceDTO;
import notehospital.dto.response.ServiceResponse;
import notehospital.entity.Service;

public class ServiceMapping {
    public static ServiceDTO MapEntityToResponse(Service service) {

        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setImage(service.getImage());
        serviceDTO.setName(service.getName());
        serviceDTO.setPrice(service.getPrice());
        serviceDTO.setDescription(service.getDescription());
        serviceDTO.setFacility(FacilityMapping.MapEntityToResponse(service.getFacilitysv()));
        return serviceDTO;

    }
}

package notehospital.dto.request;

import lombok.Data;
import notehospital.entity.PrescriptionItem;

import java.util.List;

@Data
public class CreatePrescriptionRequest {
    List<PrescriptionItemRequest> prescriptionItems;
}

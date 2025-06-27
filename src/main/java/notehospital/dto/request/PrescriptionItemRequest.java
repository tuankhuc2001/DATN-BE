package notehospital.dto.request;

import lombok.Data;

@Data
public class PrescriptionItemRequest {
    int quantity;
    String times;
    long medicineId;
}

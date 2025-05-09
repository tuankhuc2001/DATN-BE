package notehospital.dto.request;

import lombok.Data;

@Data
public class MedicineRequest {
    long id;
    String name;
    String barcode;
    String describemedicine;
}

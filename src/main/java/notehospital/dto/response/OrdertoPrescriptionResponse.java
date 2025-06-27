package notehospital.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import notehospital.entity.Account;
import notehospital.entity.Prescription;
import notehospital.entity.Service;
import notehospital.enums.OrderStatus;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrdertoPrescriptionResponse {
    long id;
    Date createdAt;
    String note;
    Date testDate;
    AccountResponseDTO doctor;
    Prescription prescription;
}

package notehospital.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import notehospital.entity.Prescription;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ScheduletoPrescriptionResponse {
    long id;
    Date createdAt;
    String note;
    Date testDate;
    AccountResponseDTO doctor;
    Prescription prescription;
}

package notehospital.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import notehospital.entity.Prescription;
import notehospital.enums.ScheduleStatus;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ScheduleResponse {
    long id;
    Date createdAt;
    String note;
    Date testDate;
    ScheduleStatus status;
    AccountResponseDTO doctor;
    AccountResponseDTO patient;
    Prescription prescription;
    Set<ResultResponse> results;
}

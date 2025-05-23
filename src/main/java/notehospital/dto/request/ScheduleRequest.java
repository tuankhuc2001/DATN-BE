package notehospital.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class ScheduleRequest {
    Date testDate;
    long doctorId;
    long[] services;
    long facilityod_id;
    String note;
    long patientId;
}

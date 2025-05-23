package notehospital.Mapping;


import notehospital.dto.response.ScheduletoPrescriptionResponse;
import notehospital.entity.Schedule;

public class ScheduletoPrescriptionMapping {
    public static ScheduletoPrescriptionResponse MapEntitytoResponse(Schedule schedule) {
        ScheduletoPrescriptionResponse scheduletoPrescriptionResponse = new ScheduletoPrescriptionResponse();
        scheduletoPrescriptionResponse.setId(schedule.getId());
        scheduletoPrescriptionResponse.setCreatedAt(schedule.getCreatedAt());
        scheduletoPrescriptionResponse.setNote(schedule.getNote());
        scheduletoPrescriptionResponse.setTestDate(schedule.getTestDate());
        scheduletoPrescriptionResponse.setDoctor(AccountMapping.accountResponseDTO(schedule.getDoctor()));
        scheduletoPrescriptionResponse.setPrescription(schedule.getPrescription());
        return scheduletoPrescriptionResponse;

    }
}
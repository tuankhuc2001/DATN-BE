package notehospital.Mapping;


import notehospital.dto.response.ScheduleResponse;
import notehospital.dto.response.ResultResponse;
import notehospital.entity.Schedule;
import notehospital.entity.Result;

import java.util.HashSet;
import java.util.Set;

public class ScheduleMapping {
    public static ScheduleResponse  MapEntitytoResponse(Schedule schedule) {
       ScheduleResponse scheduleResponse = new ScheduleResponse();
       scheduleResponse.setId(schedule.getId());
       scheduleResponse.setCreatedAt(schedule.getCreatedAt());
       scheduleResponse.setNote(schedule.getNote());
       scheduleResponse.setTestDate(schedule.getTestDate());
       scheduleResponse.setStatus(schedule.getStatus());
       scheduleResponse.setDoctor(AccountMapping.accountResponseDTO(schedule.getDoctor()));
       scheduleResponse.setPatient(AccountMapping.accountResponseDTO(schedule.getPatient()));

       Set<ResultResponse> resultResponses = new HashSet<>();
       if (schedule.getResults() != null) {
          for (Result result : schedule.getResults()) {
             resultResponses.add(ResultMapping.MapEntityToResponse(result));
          }
       }
       scheduleResponse.setPrescription(schedule.getPrescription());
       scheduleResponse.setResults(resultResponses);
       return scheduleResponse;

    }
}

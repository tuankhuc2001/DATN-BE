package notehospital.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import notehospital.Mapping.ScheduleMapping;
import notehospital.Mapping.ResultMapping;
import notehospital.dto.request.CreatePrescriptionRequest;
import notehospital.dto.request.ResultRequest;
import notehospital.dto.request.ScheduleRequest;
import notehospital.dto.response.ScheduleResponse;
import notehospital.dto.response.ResultResponse;
import notehospital.entity.Schedule;
import notehospital.enums.ScheduleStatus;
import notehospital.service.ScheduleService;
import notehospital.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    ResponseHandler responseHandler;

    @PostMapping("order")
    public ResponseEntity createOrder(@RequestBody ScheduleRequest orderRequest) throws ParseException {
        Schedule order = scheduleService.createSchedule(orderRequest);
        return responseHandler.response(201, "Tạo lịch khám mới thành công!", order);
    }

    @GetMapping("schedule")
    public ResponseEntity getSchedule() {
        List<Schedule> schedule = scheduleService.getSchedule();
        List<ScheduleResponse>scheduleResponse=schedule.stream().map(ScheduleMapping::MapEntitytoResponse).collect(Collectors.toList());
        return responseHandler.response(201, "Xem thành công!", scheduleResponse);
    }
    @GetMapping("scheduleDone")
    public ResponseEntity<?> getScheduleWithStatusDone() {
        List<Schedule> schedules = scheduleService.getSchedulesWithStatusDone();
        List<ScheduleResponse> scheduleResponses = schedules.stream().map(schedule -> {
            ScheduleResponse response = ScheduleMapping.MapEntitytoResponse(schedule);
            response.setStatus(schedule.getStatus());
            return response;
        }).collect(Collectors.toList());
        return responseHandler.response(200, "Xem thành công!", scheduleResponses);
    }

    @GetMapping("schedule/{userId}")
    public ResponseEntity getScheduleHistory(@PathVariable long userId){
        List<Schedule> schedules = scheduleService.getScheduleHistory(userId);
        List<ScheduleResponse>scheduleResponse=schedules.stream().map(ScheduleMapping::MapEntitytoResponse).collect(Collectors.toList());
        return responseHandler.response(200,"Xem thành công!", scheduleResponse);
    }

    @GetMapping("schedule-detail/{scheduleId}")
    public ResponseEntity getScheduleDetail(@PathVariable long scheduleId) {
        Schedule schedules = scheduleService.getScheduleDetail(scheduleId);
        ScheduleResponse scheduleResponse = ScheduleMapping.MapEntitytoResponse(schedules);
        return responseHandler.response(200, "Xem thành công!", scheduleResponse);
    }

    @GetMapping("schedules/{userId}")
    public ResponseEntity getSchedule(@PathVariable long userId) {
        List<Schedule> schedules = scheduleService.getDoctorSchedule(userId);
        return responseHandler.response(200, "Xem thành công!", schedules);
    }

    @GetMapping("/health-record/{userId}")
    public ResponseEntity getHealthRecord(@PathVariable long userId) {
        List<ResultResponse> results = scheduleService.getHealthRecord(userId).stream().map(ResultMapping::MapEntityToResponse).collect(Collectors.toList());
        return responseHandler.response(200, "Xem thành công!", results);
    }

    @PatchMapping("schedule/{orderId}/{status}")
    public ResponseEntity updateStatusOrder(@PathVariable long orderId, @PathVariable ScheduleStatus status) {
        Schedule orders = scheduleService.updateStatusSchedule(orderId, status);
        ScheduleResponse orderResponse = ScheduleMapping.MapEntitytoResponse(orders);
        return responseHandler.response(200, "Duyệt yêu cầu thành công!", orderResponse);
    }

    @PostMapping("/prescription/{orderId}")
    public ResponseEntity createPrescription(@PathVariable long orderId, @RequestBody CreatePrescriptionRequest createPrescriptionRequest){
        Schedule order = scheduleService.createPrescription(orderId, createPrescriptionRequest);
        return responseHandler.response(200,"Tạo mới đơn thuốc thành công!", order);
    }

    @PostMapping("/result/{orderId}")
    public ResponseEntity createResult(@PathVariable long orderId, @RequestBody List<ResultRequest> resultRequests) {
        Schedule order = scheduleService.createResult(orderId, resultRequests);
        return responseHandler.response(200, "Tạo mới kết quả thành công!", order);
    }

}

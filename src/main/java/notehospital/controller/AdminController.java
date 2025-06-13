package notehospital.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import notehospital.dto.response.AccountResponseDTO;
import notehospital.entity.Facility;
import notehospital.entity.Medicine;
import notehospital.entity.Service;
import notehospital.service.AccountService;
import notehospital.service.AdminService;
import notehospital.service.MedicineService;
import notehospital.service.ScheduleService;
import notehospital.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class AdminController {
    @Autowired
    AdminService adminService;

    @Autowired
    AccountService accountService;
    @Autowired
    ResponseHandler responseHandler;

    @Autowired
    MedicineService medicineService;
    @Autowired
    ScheduleService scheduleService;


    @GetMapping("/accounts")
    public ResponseEntity getAllAccount() {
        List<AccountResponseDTO> accountResponseDTOList = adminService.getAllAccount();
        return responseHandler.response(200, "Xem thành công!", accountResponseDTOList);
    }

    @GetMapping("/accounts/patient")
    public ResponseEntity getAccountPatient() {
        List<AccountResponseDTO> accountResponseDTOList = adminService.getAccountPatient();
        return responseHandler.response(200, "Xem thành công!", accountResponseDTOList);
    }

    @GetMapping("/patientrecord")
    public ResponseEntity getAccountPatienRecordt() {
        Set<AccountResponseDTO> accountResponseDTOList = adminService.getAccountPatientWithDoneSchedules();
        return responseHandler.response(200, "Xem thành công!", accountResponseDTOList);
    }

    @GetMapping("/accounts/doctor")
    public ResponseEntity getAccountDoctor() {
        List<AccountResponseDTO> accountResponseDTOList = adminService.getAccountDoctor();
        return responseHandler.response(200, "Xem thành công!", accountResponseDTOList);
    }

    @GetMapping("/facility")
    public ResponseEntity<?> getFacility() {
        List<Facility> facilities = accountService.getFacility(); // Sử dụng service để lấy danh sách cơ sở vật chất

        if (facilities != null && !facilities.isEmpty()) {
            return responseHandler.response(200, "Đã lấy cơ sở thành công!", facilities);
        } else {
            return responseHandler.response(404, "Đã lấy cơ sở thất bại", null);
        }
    }

    @GetMapping("/service")
    public ResponseEntity getAllService() {
        List<Service> accountResponseDTOList = adminService.getAllService();
        return responseHandler.response(200, "Xem thành công!", accountResponseDTOList);
    }

    @GetMapping("/service/facility")
    public ResponseEntity getAllServiceWithFacility() {
        List<Service> accountResponseDTOList = adminService.getAllServiceWithFacility();
        return responseHandler.response(200, "Xem thành công!", accountResponseDTOList);
    }

    @GetMapping("/medicine")
    public ResponseEntity getMedicine() {
        List<Medicine> doctors = medicineService.getAllMedicine();
        return responseHandler.response(200, "Xem thành công!", doctors);
    }
}

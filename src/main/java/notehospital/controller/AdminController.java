package notehospital.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import notehospital.Mapping.OrderMapping;
import notehospital.dto.request.FacilityRequest;
import notehospital.dto.request.MedicineRequest;
import notehospital.dto.request.ServiceRequest;
import notehospital.dto.response.AccountResponseDTO;
import notehospital.dto.response.OrderResponse;
import notehospital.entity.Facility;
import notehospital.entity.Medicine;
import notehospital.entity.Order;
import notehospital.entity.Service;
import notehospital.service.AccountService;
import notehospital.service.AdminService;
import notehospital.service.MedicineService;
import notehospital.service.OrderService;
import notehospital.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    OrderService orderService;


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
        Set<AccountResponseDTO> accountResponseDTOList = adminService.getAccountPatientWithDoneOrders();
        //aa
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
    @PostMapping("/facility")
    public ResponseEntity createFacility(@RequestBody FacilityRequest facilityRequest) {
        Facility facility = adminService.createFacility(facilityRequest);
        return responseHandler.response(201, "Thành công!", facility);
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


    @PostMapping("/service")
    public ResponseEntity createService(@RequestBody ServiceRequest serviceRequest) {
        System.out.println("Facility ID received: " + serviceRequest.getFacilityac_id());
        Service service = adminService.createService(serviceRequest);
        return responseHandler.response(201, "Thành công!", service);
    }

    @DeleteMapping("/service/{serviceId}")
    public ResponseEntity deleteService(@PathVariable long serviceId) {
        adminService.deleteService(serviceId);
        return responseHandler.response(201, "Xóa dịch vụ thành công!", null);
    }

    @GetMapping("/medicine")
    public ResponseEntity getMedicine() {
        List<Medicine> doctors = medicineService.getAllMedicine();
        return responseHandler.response(200, "Xem thành công!", doctors);
    }

    @PostMapping("/medicine")
    public ResponseEntity createMedicine(@RequestBody MedicineRequest medicineRequest) {
        Medicine medicine = adminService.createMedicine(medicineRequest);
        return responseHandler.response(201, "Thành công!", medicine);
    }

    @DeleteMapping("/medicine/{serviceId}")
    public ResponseEntity deleteMedicine(@PathVariable long serviceId) {
        adminService.deleteMedicine(serviceId);
        return responseHandler.response(201, "Xóa thuốc thành công!", null);
    }
}

package notehospital.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import javax.validation.Valid;

import notehospital.Mapping.AccountMapping;
import notehospital.Mapping.OrderMapping;
import notehospital.dto.request.*;
import notehospital.dto.response.AccountResponseDTO;
import notehospital.dto.response.OrderResponse;
import notehospital.dto.response.ResponseDTO;
import notehospital.entity.Account;
import notehospital.entity.Facility;
import notehospital.enums.AccountStatus;
import notehospital.exception.exception.BadRequest;
import notehospital.service.AccountService;
import notehospital.service.MedicineService;
import notehospital.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    ResponseHandler responseHandler;

    @Autowired
    MedicineService medicineService;

    @GetMapping("/account/{userId}")
    public ResponseEntity getUserProfile(@PathVariable long userId) {
        AccountResponseDTO accountResponseDTO = accountService.getAccountById(userId);
        return responseHandler.response(201, "Xem thành công!", accountResponseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody AccountRequestDTO accountRequestDTO) {
        AccountResponseDTO accountResponseDTO = accountService.register(accountRequestDTO);
        return responseHandler.response(201, "Đăng ký thành công tài khoản mới!", accountResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        AccountResponseDTO accountResponseDTO = accountService.login(loginRequestDTO);
        if (accountResponseDTO.getAccountStatus() == AccountStatus.INACTIVE) {
            throw new BadRequest("Tài khoản chưa được kích hoạt!");
        }
        return responseHandler.response(200, "Đăng nhập thành công!", accountResponseDTO);
    }

    @PutMapping("/profile")
    public ResponseEntity updateProfile(@RequestBody AccountRequestDTO accountRequestDTO) {
        System.out.println(">>> REQUEST: " + accountRequestDTO);
        AccountResponseDTO accountResponseDTO = accountService.updateProfile(accountRequestDTO);
        return responseHandler.response(200, "Cập nhật tài khoản thành công!", accountResponseDTO);
    }

    @PutMapping("/password/{userId}")
    public ResponseEntity updatePassword(@RequestBody UpdatePassword updatePassword, @PathVariable long userId) {
        System.out.println(updatePassword);
        System.out.println(userId);
        AccountResponseDTO accountResponseDTO = accountService.updatePassword(userId, updatePassword);
        return responseHandler.response(200, "Cập nhật mật khẩu thành công!", accountResponseDTO);
    }

    @PostMapping("/active/{userId}")
    public ResponseEntity activeAccount(@RequestBody ActiveAccount account, @PathVariable long userId) {
        AccountResponseDTO accountResponseDTO = accountService.activeAccount(userId, account);
        return responseHandler.response(200, "Kích hoạt tài khoản thành công!", accountResponseDTO);
    }

    @GetMapping("/facility-services/{facilityId}")
    public ResponseEntity<?> getFacilityServices(@PathVariable Long facilityId) {
        Map<String, Object> facilityAndServices = accountService.getServicesByFacilityId(facilityId);
        return responseHandler.response(200, "Xem thành công!", facilityAndServices);
    }

    @GetMapping("/service-doctors/{serviceId}")
    public ResponseEntity<?> getDoctorsByServiceId(@PathVariable Long serviceId) {
        List<AccountResponseDTO> doctors = accountService.getDoctorsByServiceId(serviceId);
        return responseHandler.response(200, "Xem thành công!", doctors);
    }

    @GetMapping("/info/{accountPhone}")
    public ResponseEntity getAccountByPhone(@PathVariable String accountPhone) {
        Account account = accountService.getAccountByPhone(accountPhone);
        return responseHandler.response(200, "Xem thành công!", account);
    }

    @PostMapping("reset-password/{accountId}")
    public ResponseEntity resetPassword(@PathVariable long accountId, @RequestBody ResetPassword resetPassword) {
        Account account = accountService.resetPassword(resetPassword, accountId);
        return responseHandler.response(200, "Đặt lại mật khẩu thành công!", account);
    }

    @GetMapping("/check-user/{phone}")
    public ResponseEntity checkPhoneExist(@PathVariable String phone) {
        System.out.println(phone);
        Account account = accountService.getAccountByPhone(phone);
        if (account == null) {
            return ResponseEntity.ok(new ResponseDTO(200, "Có sẵn"));
        }
        return responseHandler.response(200, "Số điện thoại đã tồn tại", account);
    }
}

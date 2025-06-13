package notehospital.service;

import notehospital.Mapping.AccountMapping;
import notehospital.dto.request.*;
import notehospital.dto.response.AccountResponseDTO;
import notehospital.dto.response.ServiceResponse;
import notehospital.entity.Account;
import notehospital.entity.Facility;
import notehospital.enums.AccountStatus;
import notehospital.enums.AccountType;
import notehospital.exception.exception.BadRequest;
import notehospital.exception.exception.EntityNotFound;
import notehospital.repository.AccountRepository;
import notehospital.repository.FacilityRepository;
import notehospital.repository.ServiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountService implements UserDetailsService {

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    EmailService emailService;

    @Autowired
    FacilityRepository facilityRepository;

    @Autowired
    ServiceRepository serviceRepository;

    public AccountResponseDTO register(AccountRequestDTO accountRequestDTO) {
        Account account = modelMapper.map(accountRequestDTO, Account.class);
        if(account.getPassword() != null && !account.getPassword().isEmpty()) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
        }
        account.setCode(UUID.randomUUID().toString().replace("-", ""));
        if(account.getType() == AccountType.DOCTOR){
            String defaultPassword = "123456";
            account.setPassword(passwordEncoder.encode(defaultPassword));
            Optional<notehospital.entity.Service> service = serviceRepository.findById(accountRequestDTO.getService_id());
            account.setServiceac(service.get());
            account.setAccountStatus(AccountStatus.ACTIVE);
        }
        Account newAccount = accountRepository.save(account);
        return modelMapper.map(newAccount, AccountResponseDTO.class);
    }

    public AccountResponseDTO  login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = null;

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequestDTO.getPhone(),
                    loginRequestDTO.getPassword()
            ));
            return modelMapper.map(authentication.getPrincipal(), AccountResponseDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EntityNotFound("Username or password invalid!");
        }
    }

    public AccountResponseDTO updateProfile(AccountRequestDTO accountRequestDTO) {
        Account oldAccount = accountRepository.findByUsernameOrPhoneOrEmail(accountRequestDTO.getPhone());
        if (oldAccount == null) {
            throw new EntityNotFound("Account not found!");
        }
        oldAccount.setPhone(accountRequestDTO.getPhone());
        oldAccount.setFullName(accountRequestDTO.getFullName());
        oldAccount.setEmail(accountRequestDTO.getEmail());
        oldAccount.setGender(accountRequestDTO.getGender());
        oldAccount.setAddress(accountRequestDTO.getAddress());
        oldAccount.setDateOfBirth(accountRequestDTO.getDateOfBirth());
        Account newAccount = accountRepository.save(oldAccount);
        return modelMapper.map(newAccount, AccountResponseDTO.class);
    }

    public AccountResponseDTO getAccountById(long id) {
        Account account = accountRepository.findAccountById(id);
        return AccountMapping.accountResponseDTO(account);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsernameOrPhoneOrEmail(username);
    }

    public AccountResponseDTO updatePassword(long userId, UpdatePassword updatePassword) {
        Account account = accountRepository.findAccountById(userId);
        account.setPassword(passwordEncoder.encode(updatePassword.getNewPassword()));
        return modelMapper.map(accountRepository.save(account), AccountResponseDTO.class);
    }

    public AccountResponseDTO activeAccount(long userId, ActiveAccount activeAccount) {
        Account account = accountRepository.findAccountById(userId);
        System.out.println(account.getCode());
        System.out.println(activeAccount.getCode());
        if (account.getCode().equals(activeAccount.getCode())) {
            account.setAccountStatus(AccountStatus.ACTIVE);
        } else {
            throw new BadRequest("Code is incorrect!");
        }

        return modelMapper.map(accountRepository.save(account), AccountResponseDTO.class);
    }

    public List<Facility> getFacility() {
        List<Facility> facilities = facilityRepository.findAll();
        return facilities;
    }

    public Map<String, Object> getServicesByFacilityId(Long facilityId) {
        Facility facility = facilityRepository.findById(facilityId).orElse(null);
        if (facility == null) {
        }
        List<notehospital.entity.Service> services = serviceRepository.findServicesByFacilityId(facilityId);
        List<ServiceResponse> serviceDTOs = services.stream()
                .map(service ->{
                    ServiceResponse serviceResponse =new ServiceResponse();
                    BeanUtils.copyProperties(service,serviceResponse);
                    serviceResponse.setFacility(service.getFacilitysv());
                    return serviceResponse;
                })
                .collect(Collectors.toList());

        Map<String, Object> facilityAndServices = new HashMap<>();
        facilityAndServices.put("services", serviceDTOs);
        return facilityAndServices;
    }

    public List<AccountResponseDTO> getDoctorsByServiceId(Long serviceId) {
        List<Account> doctors = accountRepository.findDoctorsByServiceId(AccountType.DOCTOR, serviceId);
        return doctors.stream()
                .map(doctor -> modelMapper.map(doctor, AccountResponseDTO.class))
                .collect(Collectors.toList());
    }


    public Account getAccountByPhone(String phone) {
        Account account = accountRepository.findByUsernameOrPhoneOrEmail(phone);
        if (account == null) {
            throw new EntityNotFound("Account not found");
        }
        return account;
    }

    public Account resetPassword(ResetPassword resetPassword, long accountId) {
        Account account = accountRepository.findAccountById(accountId);
        account.setPassword(passwordEncoder.encode(resetPassword.getNewPassword()));
        return accountRepository.save(account);
    }
}

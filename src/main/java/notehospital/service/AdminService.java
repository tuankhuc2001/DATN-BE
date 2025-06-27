package notehospital.service;

import notehospital.Mapping.OrderMapping;
import notehospital.dto.request.FacilityRequest;
import notehospital.dto.request.MedicineRequest;
import notehospital.dto.request.ServiceRequest;
import notehospital.dto.response.*;
import notehospital.entity.Account;
import notehospital.entity.Facility;
import notehospital.entity.Medicine;
import notehospital.entity.Order;
import notehospital.enums.AccountType;
import notehospital.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminService {
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ServiceRepository   serviceRepository;

    @Autowired
    FacilityRepository facilityRepository;

    @Autowired
    MedicineRepository medicineRepository;

    @Autowired
    OrderRepository orderRepository;

    public List<AccountResponseDTO> getAllAccount(){
        List<Account> accounts = accountRepository.findAll();
        List<AccountResponseDTO> accountResponseDTOS = new ArrayList<>();
        for(Account account: accounts){
            accountResponseDTOS.add(modelMapper.map(account, AccountResponseDTO.class));
        }
        return accountResponseDTOS;
    }


    public List<AccountResponseDTO> getAccountPatient(){
        List<Account> accounts = accountRepository.findPatients();
        List<AccountResponseDTO> accountResponseDTOS = new ArrayList<>();
        for(Account account: accounts){
            accountResponseDTOS.add(modelMapper.map(account, AccountResponseDTO.class));
        }
        return accountResponseDTOS;
    }


    public Set<AccountResponseDTO> getAccountPatientWithDoneOrders() {
        List<Account> accounts = accountRepository.findPatientsWithDoneOrders();
        List<Account> sortedAccounts = accounts.stream()
                .sorted(Comparator.comparingLong(Account::getId))
                .collect(Collectors.toList());
        Set<AccountResponseDTO> accountResponseDTOS = new HashSet<>();
        for (Account account : sortedAccounts) {
            List<Order> doneOrders = orderRepository.findDoneOrdersByPatient(account.getId());
            if (!doneOrders.isEmpty()) {
                AccountResponseDTO accountResponseDTO = modelMapper.map(account, AccountResponseDTO.class);
                Set<OrderResponse> orderResponseDTOS = doneOrders.stream()
                        .map(OrderMapping::MapEntitytoResponse)
                        .collect(Collectors.toSet());
                accountResponseDTO.setOrderResponses(orderResponseDTOS);
                accountResponseDTOS.add(accountResponseDTO);
            }
        }
        return accountResponseDTOS;
    }

    public List<AccountResponseDTO> getAccountDoctor(){
        List<Account> accounts = accountRepository.findDoctors();
        List<AccountResponseDTO> accountResponseDTOS = new ArrayList<>();

        for(Account account: accounts){
            AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
            accountResponseDTO.setAccountType(account.getType());
            BeanUtils.copyProperties(account, accountResponseDTO);
            // Tạo một ServiceDTO từ Service
            notehospital.entity.Service service = account.getServiceac();
            ServiceDTO serviceDTO = new ServiceDTO();
            if(service==null){
                accountResponseDTO.setService(null);
            }
            if(service!=null){
                serviceDTO.setId(service.getId());
                serviceDTO.setImage(service.getImage());
                serviceDTO.setName(service.getName());
                serviceDTO.setPrice(service.getPrice());
                serviceDTO.setDescription(service.getDescription());
                accountResponseDTO.setService(serviceDTO); // Gán ServiceDTO vào AccountResponseDTO

                Facility facility = account.getServiceac().getFacilitysv(); // Đây là ví dụ, bạn cần sử dụng phương thức thích hợp để lấy dữ liệu Facility từ Service
                FacilityResponse facilityResponse = new FacilityResponse();
                facilityResponse.setFacility_name(facility.getFacility_name());
                serviceDTO.setFacility(facilityResponse);
            }
            accountResponseDTOS.add(accountResponseDTO);

        }

        return accountResponseDTOS;
    }

    public List<notehospital.entity.Service> getAllService(){
        List<notehospital.entity.Service> services = serviceRepository.findAllServicesWithFacility();
        return services;
    }

    public List<notehospital.entity.Service> getAllServiceWithFacility(){
        List<notehospital.entity.Service> services = serviceRepository.findAllServicesWithFacility();
        return services;
    }

    public notehospital.entity.Service createService(ServiceRequest serviceRequest){
        notehospital.entity.Service service = modelMapper.map(serviceRequest, notehospital.entity.Service.class);
        Optional<Facility>  facility = facilityRepository.findById(serviceRequest.getFacilityac_id());
        service.setFacilitysv(facility.get());
        return serviceRepository.save(service);
    }

    public void deleteService(long serviceId){
        serviceRepository.deleteById(serviceId);
    }

    public Facility createFacility(FacilityRequest facilityRequest){
        Facility facility = modelMapper.map(facilityRequest, Facility.class);
        return facilityRepository.save(facility);
    }

    public Medicine createMedicine(MedicineRequest medicineRequest){
        Medicine medicine = modelMapper.map(medicineRequest, Medicine.class);
        return medicineRepository.save(medicine);
    }

    public void deleteMedicine(long medicineId){
        medicineRepository.deleteById(medicineId);
    }


}

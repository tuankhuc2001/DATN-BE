package notehospital.Mapping;

import notehospital.dto.response.*;
import notehospital.entity.Account;
import notehospital.entity.Order;
import notehospital.entity.Result;
import notehospital.enums.AccountType;

import java.util.HashSet;
import java.util.Set;

public class AccountMapping {
    public static AccountResponseDTO accountResponseDTO(Account account){
        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        accountResponseDTO.setId(account.getId());
        accountResponseDTO.setFullName(account.getFullName());
        accountResponseDTO.setEmail(account.getEmail());
        accountResponseDTO.setPhone(account.getPhone());
        accountResponseDTO.setGender(account.getGender());
        accountResponseDTO.setAddress(account.getAddress());
        accountResponseDTO.setDateOfBirth(account.getDateOfBirth());
        accountResponseDTO.setAccountType(account.getType());
        if(account.getType() == AccountType.DOCTOR){
            accountResponseDTO.setService(ServiceMapping.MapEntityToResponse(account.getServiceac()));
        }
        return accountResponseDTO;
    }

}

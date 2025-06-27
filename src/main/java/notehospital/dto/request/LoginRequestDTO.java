package notehospital.dto.request;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String phone;
    private String password;
}

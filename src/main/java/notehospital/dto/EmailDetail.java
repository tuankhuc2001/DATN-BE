package notehospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetail {
    private String name;
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
    private String code;
    private String password;
}

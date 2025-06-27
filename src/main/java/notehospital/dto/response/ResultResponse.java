package notehospital.dto.response;

import lombok.*;
import notehospital.entity.Service;
import notehospital.enums.ResultLevel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ResultResponse {
    long id;
    String comment;
    String value;
    ResultLevel level;
    private OrdertoPrescriptionResponse ordertoPrescription;

}

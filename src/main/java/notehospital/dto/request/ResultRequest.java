package notehospital.dto.request;

import lombok.Data;
import notehospital.enums.ResultLevel;

@Data
public class ResultRequest {
    long id;
    ResultLevel level;
    String comment;
    String value;
}

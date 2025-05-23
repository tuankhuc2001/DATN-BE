package notehospital.utils;

import notehospital.dto.response.ResponseDTO;
import notehospital.dto.response.ResponseWithDataDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseHandler<T> {

    public ResponseEntity response(int code, String message, T data){
        return ResponseEntity.ok(new ResponseWithDataDTO(code, message, data));
    }

}

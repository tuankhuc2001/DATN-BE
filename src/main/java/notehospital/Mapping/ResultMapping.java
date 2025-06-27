package notehospital.Mapping;

import notehospital.dto.response.ResultResponse;
import notehospital.entity.Result;

public class ResultMapping {
    public static ResultResponse MapEntityToResponse(Result result){
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setId(result.getId());
        resultResponse.setComment(result.getComment());
        resultResponse.setValue(result.getValue());
        resultResponse.setLevel(result.getLevel());
        resultResponse.setOrdertoPrescription(OrdertoPrescriptionMapping.MapEntitytoResponse(result.getOrder()));
        return resultResponse;
    }
}

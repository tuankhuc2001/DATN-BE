package notehospital.Mapping;


import notehospital.dto.response.OrderResponse;
import notehospital.dto.response.OrdertoPrescriptionResponse;
import notehospital.dto.response.ResultResponse;
import notehospital.entity.Order;
import notehospital.entity.Result;

import java.util.HashSet;
import java.util.Set;

public class OrdertoPrescriptionMapping {
    public static OrdertoPrescriptionResponse MapEntitytoResponse(Order order) {
        OrdertoPrescriptionResponse ordertoPrescriptionResponse = new OrdertoPrescriptionResponse();
        ordertoPrescriptionResponse.setId(order.getId());
        ordertoPrescriptionResponse.setCreatedAt(order.getCreatedAt());
        ordertoPrescriptionResponse.setNote(order.getNote());
        ordertoPrescriptionResponse.setTestDate(order.getTestDate());
        ordertoPrescriptionResponse.setDoctor(AccountMapping.accountResponseDTO(order.getDoctor()));
        ordertoPrescriptionResponse.setPrescription(order.getPrescription());
        return ordertoPrescriptionResponse;

    }
}
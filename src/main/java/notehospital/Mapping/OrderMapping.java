package notehospital.Mapping;


import notehospital.dto.response.OrderResponse;
import notehospital.dto.response.ResultResponse;
import notehospital.entity.Order;
import notehospital.entity.Result;

import java.util.HashSet;
import java.util.Set;

public class OrderMapping {
    public static OrderResponse MapEntitytoResponse(Order order) {
       OrderResponse orderResponse = new OrderResponse();
       orderResponse.setId(order.getId());
       orderResponse.setCreatedAt(order.getCreatedAt());
       orderResponse.setNote(order.getNote());
       orderResponse.setTestDate(order.getTestDate());
       orderResponse.setStatus(order.getStatus());
       orderResponse.setDoctor(AccountMapping.accountResponseDTO(order.getDoctor()));
       orderResponse.setPatient(AccountMapping.accountResponseDTO(order.getPatient()));

       Set<ResultResponse> resultResponses = new HashSet<>();
       if (order.getResults() != null) {
          for (Result result : order.getResults()) {
             resultResponses.add(ResultMapping.MapEntityToResponse(result));
          }
       }
       orderResponse.setPrescription(order.getPrescription());
       orderResponse.setResults(resultResponses);
       return orderResponse;

    }
}

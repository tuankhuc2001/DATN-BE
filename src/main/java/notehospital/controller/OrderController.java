package notehospital.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import notehospital.Mapping.OrderMapping;
import notehospital.Mapping.ResultMapping;
import notehospital.dto.request.CreatePrescriptionRequest;
import notehospital.dto.request.OrderRequest;
import notehospital.dto.request.ResultRequest;
import notehospital.dto.response.AccountResponseDTO;
import notehospital.dto.response.OrderResponse;
import notehospital.dto.response.ResultResponse;
import notehospital.entity.Order;
import notehospital.enums.OrderStatus;
import notehospital.service.AdminService;
import notehospital.service.OrderService;
import notehospital.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    ResponseHandler responseHandler;

    @PostMapping("order")
    public ResponseEntity createOrder(@RequestBody OrderRequest orderRequest) throws ParseException {
        Order order = orderService.createOrder(orderRequest);
        return responseHandler.response(201, "Tạo lịch khám mới thành công!", order);
    }

    @DeleteMapping("order/{orderId}")
    public ResponseEntity deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrderById(orderId);
        return responseHandler.response(200, "Đã xóa thành công lịch khám " + orderId, null);
    }

    @GetMapping("order")
    public ResponseEntity getOrder() {
        List<Order> order = orderService.getOrder();
        List<OrderResponse>orderResponse=order.stream().map(OrderMapping::MapEntitytoResponse).collect(Collectors.toList());
        return responseHandler.response(201, "Xem thành công!", orderResponse);
    }
    @GetMapping("orderDone")
    public ResponseEntity<?> getOrderWithStatusDone() {
        List<Order> orders = orderService.getOrdersWithStatusDone();
        List<OrderResponse> orderResponses = orders.stream().map(order -> {
            OrderResponse response = OrderMapping.MapEntitytoResponse(order);
            response.setStatus(order.getStatus());
            return response;
        }).collect(Collectors.toList());
        return responseHandler.response(200, "Xem thành công!", orderResponses);
    }

    @GetMapping("order/{userId}")
    public ResponseEntity getOrderHistory(@PathVariable long userId){
        List<Order> orders = orderService.getOrderHistory(userId);
        List<OrderResponse>orderResponse=orders.stream().map(OrderMapping::MapEntitytoResponse).collect(Collectors.toList());
        return responseHandler.response(200,"Xem thành công!", orderResponse);
    }

    @GetMapping("order-detail/{orderId}")
    public ResponseEntity getOrderDetail(@PathVariable long orderId) {
        Order orders = orderService.getOrderDetail(orderId);
        OrderResponse orderResponse = OrderMapping.MapEntitytoResponse(orders);
        return responseHandler.response(200, "Xem thành công!", orderResponse);
    }

    @GetMapping("schedule/{userId}")
    public ResponseEntity getSchedule(@PathVariable long userId) {
        List<Order> orders = orderService.getDoctorSchedule(userId);
        return responseHandler.response(200, "Xem thành công!", orders);
    }

    @PatchMapping("order/{orderId}/{status}")
    public ResponseEntity updateStatusOrder(@PathVariable long orderId, @PathVariable OrderStatus status) {
        Order orders = orderService.updateStatusOrder(orderId, status);
        OrderResponse orderResponse = OrderMapping.MapEntitytoResponse(orders);
        return responseHandler.response(200, "Duyệt yêu cầu thành công!", orderResponse);
    }

    @PostMapping("/prescription/{orderId}")
    public ResponseEntity createPrescription(@PathVariable long orderId, @RequestBody CreatePrescriptionRequest createPrescriptionRequest){
        Order order = orderService.createPrescription(orderId, createPrescriptionRequest);
        return responseHandler.response(200,"Tạo mới đơn thuốc thành công!", order);
    }

    @PostMapping("/result/{orderId}")
    public ResponseEntity createResult(@PathVariable long orderId, @RequestBody List<ResultRequest> resultRequests) {
:        Order order = orderService.createResult(orderId, resultRequests);
        return responseHandler.response(200, "Tạo mới kết quả thành công!", order);
    }

    @GetMapping("/health-record/{userId}")
    public ResponseEntity getHealthRecord(@PathVariable long userId) {
        List<ResultResponse> results = orderService.getHealthRecord(userId).stream().map(ResultMapping::MapEntityToResponse).collect(Collectors.toList());
        return responseHandler.response(200, "Xem thành công!", results);
    }
}

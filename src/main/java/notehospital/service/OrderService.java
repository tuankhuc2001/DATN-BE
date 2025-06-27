package notehospital.service;

import notehospital.dto.request.CreatePrescriptionRequest;
import notehospital.dto.request.OrderRequest;
import notehospital.dto.request.PrescriptionItemRequest;
import notehospital.dto.request.ResultRequest;
import notehospital.entity.*;
import notehospital.enums.OrderStatus;
import notehospital.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    ResultRepository resultRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PrescriptionRepository prescriptionRepository;
    @Autowired
    PrescriptionItemRepository prescriptionItemRepository;

    @Autowired
    MedicineRepository medicineRepository;

    @Autowired
    FacilityRepository facilityRepository;

    public Order createOrder(OrderRequest orderRequest) throws ParseException {
        Order neworder = new Order();
        Order order = orderRepository.save(neworder);
        Set<Result> results = new HashSet<>();
        Account doctor = accountRepository.findAccountById(orderRequest.getDoctorId());
        Account patient = accountRepository.findAccountById(orderRequest.getPatientId());
        for (long serviceId : orderRequest.getServices()) {
            notehospital.entity.Service service = serviceRepository.findServiceById(serviceId);
            Result result = new Result();
            result.setService(service);
            result.setOrder(order);
            results.add(resultRepository.save(result));
        }
        order.setCreatedAt(new Date());
        order.setNote(orderRequest.getNote());
        order.setTestDate(orderRequest.getTestDate());
        order.setPatient(patient);
        order.setDoctor(doctor);
        order.setResults(results);
        orderRepository.save(order);
        return null;
    }

    public void deleteOrderById(Long orderId) {
        Order order = orderRepository.findOrderByIdAndStatus(orderId);
        if (order != null) {
            orderRepository.delete(order);
        }
    }

    public List<Order> getOrderHistory(long userId) {
        Account account = accountRepository.findAccountById(userId);
        List<Order> orders = orderRepository.findOrdersByPatient(account);
        return orders;
    }


    public Order getOrderDetail(long orderId) {
        Order order = orderRepository.findOrderById(orderId);
        return order;
    }

    public List<Order> getOrder() {
        Sort sortByDescendingId = Sort.by(Sort.Direction.DESC, "id");
        List<Order> orders = orderRepository.findAll(sortByDescendingId);
        return orders;
    }

    public List<Order> getOrdersWithStatusDone() {
        return orderRepository.findOrdersByStatus(OrderStatus.DONE);
    }

    public Order updateStatusOrder(long orderId, OrderStatus accountStatus) {
        Order order = orderRepository.findOrderById(orderId);
        order.setStatus(accountStatus);
        return orderRepository.save(order);
    }

    public List<Order> getDoctorSchedule(long doctorId) {
        Account account = accountRepository.findAccountById(doctorId);
        List<Order> orders = orderRepository.findOrdersByDoctorAndStatus(account, OrderStatus.IN_PROCESS);
        return orders;
    }

    public Order createPrescription(long orderId, CreatePrescriptionRequest createPrescriptionRequest) {
        Order order = orderRepository.findOrderById(orderId);

        if (order.getPrescription() == null) {
            Prescription prescription = new Prescription();
            prescription.setOrder(order);
            prescription = prescriptionRepository.save(prescription);
            order.setPrescription(prescription);
            order = orderRepository.save(order);
        }

        if (order.getPrescription() != null && order.getPrescription().getPrescriptionItems() != null &&
                order.getPrescription().getPrescriptionItems().size() > 0
        ) {
            for (PrescriptionItem prescriptionItem : order.getPrescription().getPrescriptionItems()) {
                prescriptionItem.setDeleted(true);
                prescriptionItemRepository.save(prescriptionItem);
            }
        }

        for (PrescriptionItemRequest prescriptionItem : createPrescriptionRequest.getPrescriptionItems()) {
            Medicine medicine = medicineRepository.findMedicineById(prescriptionItem.getMedicineId());
            PrescriptionItem newItem = new PrescriptionItem();
            newItem.setTimes(prescriptionItem.getTimes());
            newItem.setQuantity(prescriptionItem.getQuantity());
            newItem.setMedicine(medicine);
            newItem.setPrescription(order.getPrescription());
            prescriptionItemRepository.save(newItem);
        }

        Order newOrder = orderRepository.findOrderById(orderId);
        return newOrder;
    }

    public Order createResult(long orderId, List<ResultRequest> results) {
        for (ResultRequest result : results) {
            Result result1 = resultRepository.findResultById(result.getId());
            result1.setLevel(result.getLevel());
            result1.setValue(result.getValue());
            result1.setComment(result.getComment());
            resultRepository.save(result1);
        }
        return orderRepository.findOrderById(orderId);
    }

    public List<Result> getHealthRecord(long userId) {
        List<Result> results = resultRepository.getHealthRecord(userId);
        return results;
    }

}

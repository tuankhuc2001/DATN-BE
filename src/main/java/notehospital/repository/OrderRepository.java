package notehospital.repository;

import notehospital.entity.Account;
import notehospital.entity.Order;
import notehospital.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrdersByPatient(Account patient);

    List<Order> findOrdersByStatus(OrderStatus status); //mới
    List<Order> findOrdersByDoctorAndStatus(Account doctor, OrderStatus orderStatus);

    Order findOrderById(long orderId);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH  o.doctor")
    List<Order> findOrderDoctors();

    @Query("SELECT o FROM Order o WHERE o.status='CONFIRM'")
    List<Order> findAllConfirm();

    @Query("SELECT o FROM Order o WHERE o.id = :orderId AND o.status = 'CONFIRM'")
    Order findOrderByIdAndStatus(@Param("orderId") Long orderId);

    @Query("SELECT o FROM Order o WHERE o.patient.id = :patientId AND o.status = notehospital.enums.OrderStatus.DONE")
    List<Order> findDoneOrdersByPatient(@Param("patientId") long patientId);

}

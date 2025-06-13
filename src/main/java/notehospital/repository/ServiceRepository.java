package notehospital.repository;

import notehospital.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    Service findServiceById(long id);


    @Query("SELECT s FROM Service s LEFT JOIN FETCH s.facilitysv")
    List<Service> findAllServicesWithFacility();

    @Query("SELECT s FROM Service s LEFT JOIN FETCH s.facilitysv LEFT JOIN FETCH s.accounts ")
    List<Service> findAllServicesWithFacilityAndAccounts();

    @Query("SELECT s FROM Service s WHERE s.facilitysv.id = :facilityId")
    List<Service> findServicesByFacilityId(@Param("facilityId") Long facilityId);
}


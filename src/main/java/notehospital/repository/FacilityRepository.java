package notehospital.repository;

import notehospital.entity.Account;
import notehospital.entity.Facility;
import notehospital.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FacilityRepository extends JpaRepository<Facility, Long>{
    Facility findFacilityById(long id);
    @Query("SELECT COUNT(s) FROM Service s WHERE s.facilitysv.id = :facilityId")
    long countServicesByFacilityId(@Param("facilityId") long facilityId);
}

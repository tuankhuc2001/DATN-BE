package notehospital.repository;

import notehospital.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    Result findResultById(long id);

    @Query("SELECT r FROM Result r JOIN r.order o JOIN o.patient p WHERE p.id = ?1 AND o.status='DONE'")
    List<Result> getHealthRecord(long userId);

}

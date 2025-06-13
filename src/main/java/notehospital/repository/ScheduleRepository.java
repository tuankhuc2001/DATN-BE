package notehospital.repository;

import notehospital.entity.Account;
import notehospital.entity.Schedule;
import notehospital.enums.ScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findSchedulesByPatient(Account patient);

    List<Schedule> findSchedulesByStatus(ScheduleStatus status); //mới
    List<Schedule> findSchedulesByDoctorAndStatus(Account doctor, ScheduleStatus scheduleStatus);

    Schedule findScheduleById(long scheduleId);

    @Query("SELECT o FROM Schedule o LEFT JOIN FETCH  o.doctor")
    List<Schedule> findScheduleDoctors();

    @Query("SELECT o FROM Schedule o WHERE o.status='CONFIRM'")
    List<Schedule> findAllConfirm();

    @Query("SELECT o FROM Schedule o WHERE o.id = :scheduleId AND o.status = 'CONFIRM'")
    Schedule findScheduleByIdAndStatus(@Param("scheduleId") Long scheduleId);

    @Query("SELECT o FROM Schedule o WHERE o.patient.id = :patientId AND o.status = notehospital.enums.ScheduleStatus.DONE")
    List<Schedule> findDoneSchedulesByPatient(@Param("patientId") long patientId);

}

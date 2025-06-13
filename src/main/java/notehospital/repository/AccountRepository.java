package notehospital.repository;

import notehospital.entity.Account;
import notehospital.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT u FROM Account u WHERE u.phone = ?1 OR u.email = ?1")
    Account findByUsernameOrPhoneOrEmail(String usernameOrPhoneOrEmail);

    Account findAccountById(long id);
    @Query("SELECT d FROM Account d WHERE d.type = :accountType AND d.serviceac.id = :serviceId")
    List<Account> findDoctorsByServiceId(@Param("accountType") AccountType accountType,
                                         @Param("serviceId") Long serviceId);

    @Query("SELECT a FROM Account a WHERE a.type = notehospital.enums.AccountType.PATIENT")
    List<Account> findPatients();

    @Query("SELECT a FROM Account a LEFT JOIN FETCH  a.serviceac s WHERE a.type = notehospital.enums.AccountType.DOCTOR")
    List<Account> findDoctors();

    List<Account> findAccountsByType(AccountType accountType  );

    @Query("SELECT DISTINCT o.patient FROM Schedule o WHERE o.status = notehospital.enums.ScheduleStatus.DONE")
    List<Account> findPatientsWithDoneSchedules();

}

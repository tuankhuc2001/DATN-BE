package notehospital.service;

import notehospital.dto.request.CreatePrescriptionRequest;
import notehospital.dto.request.ScheduleRequest;
import notehospital.dto.request.PrescriptionItemRequest;
import notehospital.dto.request.ResultRequest;
import notehospital.entity.*;
import notehospital.enums.ScheduleStatus;
import notehospital.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
public class ScheduleService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    ResultRepository resultRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PrescriptionRepository prescriptionRepository;
    @Autowired
    PrescriptionItemRepository prescriptionItemRepository;

    @Autowired
    MedicineRepository medicineRepository;

    @Autowired
    FacilityRepository facilityRepository;

    public Schedule createSchedule(ScheduleRequest scheduleRequest) throws ParseException {
        Schedule newschedule = new Schedule();
        Schedule schedule = scheduleRepository.save(newschedule);
        Set<Result> results = new HashSet<>();
        Account doctor = accountRepository.findAccountById(scheduleRequest.getDoctorId());
        Account patient = accountRepository.findAccountById(scheduleRequest.getPatientId());
        for (long serviceId : scheduleRequest.getServices()) {
            notehospital.entity.Service service = serviceRepository.findServiceById(serviceId);
            Result result = new Result();
            result.setService(service);
            result.setSchedule(schedule);
            results.add(resultRepository.save(result));
        }
        schedule.setCreatedAt(new Date());
        schedule.setNote(scheduleRequest.getNote());
        schedule.setTestDate(scheduleRequest.getTestDate());
        schedule.setPatient(patient);
        schedule.setDoctor(doctor);
        schedule.setResults(results);
        scheduleRepository.save(schedule);
        return null;
    }

    public void deleteScheduleById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findScheduleByIdAndStatus(scheduleId);
        if (schedule != null) {
            scheduleRepository.delete(schedule);
        }
    }

    public List<Schedule> getScheduleHistory(long userId) {
        Account account = accountRepository.findAccountById(userId);
        List<Schedule> schedules = scheduleRepository.findSchedulesByPatient(account);
        return schedules;
    }


    public Schedule getScheduleDetail(long scheduleId) {
        Schedule schedule = scheduleRepository.findScheduleById(scheduleId);
        return schedule;
    }

    public List<Schedule> getSchedule() {
        Sort sortByDescendingId = Sort.by(Sort.Direction.DESC, "id");
        List<Schedule> schedules = scheduleRepository.findAll(sortByDescendingId);
        return schedules;
    }

    public List<Schedule> getSchedulesWithStatusDone() {
        return scheduleRepository.findSchedulesByStatus(ScheduleStatus.DONE);
    }

    public Schedule updateStatusSchedule(long scheduleId, ScheduleStatus accountStatus) {
        Schedule schedule = scheduleRepository.findScheduleById(scheduleId);
        schedule.setStatus(accountStatus);
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getDoctorSchedule(long doctorId) {
        Account account = accountRepository.findAccountById(doctorId);
        List<Schedule> schedules = scheduleRepository.findSchedulesByDoctorAndStatus(account, ScheduleStatus.IN_PROCESS);
        return schedules;
    }

    public Schedule createPrescription(long scheduleId, CreatePrescriptionRequest createPrescriptionRequest) {
        Schedule schedule = scheduleRepository.findScheduleById(scheduleId);

        if (schedule.getPrescription() == null) {
            Prescription prescription = new Prescription();
            prescription.setSchedule(schedule);
            prescription = prescriptionRepository.save(prescription);
            schedule.setPrescription(prescription);
            schedule = scheduleRepository.save(schedule);
        }

        if (schedule.getPrescription() != null && schedule.getPrescription().getPrescriptionItems() != null &&
                schedule.getPrescription().getPrescriptionItems().size() > 0
        ) {
            for (PrescriptionItem prescriptionItem : schedule.getPrescription().getPrescriptionItems()) {
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
            newItem.setPrescription(schedule.getPrescription());
            prescriptionItemRepository.save(newItem);
        }

        Schedule newSchedule = scheduleRepository.findScheduleById(scheduleId);
        return newSchedule;
    }

    public Schedule createResult(long scheduleId, List<ResultRequest> results) {
        for (ResultRequest result : results) {
            Result result1 = resultRepository.findResultById(result.getId());
            result1.setLevel(result.getLevel());
            result1.setValue(result.getValue());
            result1.setComment(result.getComment());
            resultRepository.save(result1);
        }
        return scheduleRepository.findScheduleById(scheduleId);
    }

    public List<Result> getHealthRecord(long userId) {
        List<Result> results = resultRepository.getHealthRecord(userId);
        return results;
    }

}

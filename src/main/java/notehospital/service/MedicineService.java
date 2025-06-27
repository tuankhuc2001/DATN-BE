package notehospital.service;

import notehospital.entity.Medicine;
import notehospital.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {

    @Autowired
    MedicineRepository medicineRepository;

    public List<Medicine> getAllMedicine(){
        return medicineRepository.findAll();
    }

}

package timuraya.invoice_biaya_operational.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import timuraya.invoice_biaya_operational.entity.Biodata;
import timuraya.invoice_biaya_operational.repository.BiodataRepository;

import java.util.List;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 05/06/22
 **/

@Service
@RequiredArgsConstructor
public class BiodataService {


    private final BiodataRepository biodataRepository;

    public Biodata createBiodata(Biodata biodata) {
        return biodataRepository.save(biodata);
    }

    public List<Biodata> getBiodata(){
        return biodataRepository.findAll();
    }
}

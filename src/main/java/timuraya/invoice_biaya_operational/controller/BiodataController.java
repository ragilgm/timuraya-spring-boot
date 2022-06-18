package timuraya.invoice_biaya_operational.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import timuraya.invoice_biaya_operational.entity.Biodata;
import timuraya.invoice_biaya_operational.service.BiodataService;

import java.util.List;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 05/06/22
 **/

@RestController
@RequestMapping("/biodata")
@RequiredArgsConstructor
public class BiodataController extends BaseController {

    private final BiodataService biodataService;

    @PostMapping
    public Biodata createBiodata(@RequestBody Biodata biodata){
        return biodataService.createBiodata(biodata);
    }

    @GetMapping
    public List<Biodata> getBiodata(){
        return biodataService.getBiodata();
    }



}

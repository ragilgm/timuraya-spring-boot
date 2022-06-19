package timuraya.invoice_biaya_operational.controller;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 19/06/22
 **/

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import timuraya.invoice_biaya_operational.dto.ReportDto;
import timuraya.invoice_biaya_operational.service.ReportService;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController  extends BaseController{

    private final ReportService reportService;

    @GetMapping()
    private ReportDto getReport(){
     return reportService.getReport();
    }


}

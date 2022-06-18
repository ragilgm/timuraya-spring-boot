package timuraya.invoice_biaya_operational.controller;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import timuraya.invoice_biaya_operational.dto.HistoryPengajuanDto;
import timuraya.invoice_biaya_operational.dto.HistoryPengajuanRequestDto;
import timuraya.invoice_biaya_operational.service.HistoryPengajuanService;

import java.io.IOException;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 05/06/22
 **/
@RestController
@RequestMapping("/history-pengajuan")
@RequiredArgsConstructor
public class HistoryPengajuanController extends BaseController  {

    private final HistoryPengajuanService historyPengajuanService;

    @PostMapping
    public HistoryPengajuanDto createHistoryPengajuan(@RequestBody HistoryPengajuanRequestDto pengajuanRequestDto) throws NotFoundException, IOException {
        return  historyPengajuanService.createHistoryPengajuan(pengajuanRequestDto);
    }


}

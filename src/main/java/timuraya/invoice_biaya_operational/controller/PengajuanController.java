package timuraya.invoice_biaya_operational.controller;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import timuraya.invoice_biaya_operational.dto.PengajuanDto;
import timuraya.invoice_biaya_operational.dto.PengajuanRequestDto;
import timuraya.invoice_biaya_operational.dto.ValidasiPengajuanDto;
import timuraya.invoice_biaya_operational.service.PengajuanService;

import java.util.List;
import java.util.UUID;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 05/06/22
 **/
@RestController
@RequestMapping("/pengajuan")
@RequiredArgsConstructor
@Slf4j
public class PengajuanController extends BaseController  {

    private final PengajuanService pengajuanService;

    @PostMapping
    public PengajuanDto createPengajuan(@RequestBody PengajuanRequestDto pengajuanRequestDto) throws NotFoundException {
        return  pengajuanService.createPengajuan(pengajuanRequestDto);
    }

    @PutMapping("/{id}")
    public PengajuanDto updatePengajuan(@PathVariable("id") Long id ,@RequestBody PengajuanRequestDto pengajuanRequestDto) throws NotFoundException {
        log.info("edit "+id);
        log.info("edit "+pengajuanRequestDto);
       return  pengajuanService.updatePengajuan(id,pengajuanRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deletePengajuan(@PathVariable("id") Long id) throws NotFoundException {
          pengajuanService.deletePengajuan(id);
    }


    @GetMapping
    public List<PengajuanDto> getPengajuan(PengajuanRequestDto pengajuanRequestDto) {
        return  pengajuanService.getPengajuan(pengajuanRequestDto);
    }

    @GetMapping("/{id}")
    public PengajuanDto getPengajuanById(@PathVariable("id") Long id) throws NotFoundException {
        return  pengajuanService.getPengajuanById(id);
    }

    @GetMapping(value = "/letter/{id}",produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody byte[] getLetterFile(@PathVariable("id") Long id) throws NotFoundException {
        return pengajuanService.getLetter(id);
    }


    @GetMapping("/letter/{id}/download")
    public ResponseEntity<Resource> downloadLetterFile(@PathVariable("id") long id) throws NotFoundException {
        InputStreamResource inputStreamResource = pengajuanService.downloadLetter(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +UUID.randomUUID()+".pdf")
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(inputStreamResource);
    }

    @GetMapping("/letter/{id}/validate")
    public ValidasiPengajuanDto validateLetterFile(@PathVariable("id") long id) throws NotFoundException {
        return pengajuanService.validasiPengajuan(id);
    }


}

package timuraya.invoice_biaya_operational.service;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import timuraya.invoice_biaya_operational.dto.ReportDto;
import timuraya.invoice_biaya_operational.repository.PengajuanRepository;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 19/06/22
 **/

@Service
@RequiredArgsConstructor
public class ReportService {

    private final PengajuanRepository pengajuanRepository;
    private final MapperFacade mapperFacade;


    private String getDateFormat(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return localDateTime.format(formatter);
    }

    public ReportDto getReport() {
        ReportDto reportDto = new ReportDto();
        Map<String, List<ReportDto.ReportPengajuanDto>> map = new HashMap<>();
        AtomicReference<Integer> total = new AtomicReference<>(0);
        pengajuanRepository.findAll().forEach(data-> {
            LocalDate dateTime = data.getTanggalDibuat().toLocalDate();
            LocalDate dateFrom = dateTime.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate dateTo = dateTime.with(TemporalAdjusters.lastDayOfMonth());
            LocalDateTime localDateTimeFrom = LocalDateTime.from(dateFrom.atStartOfDay());
            LocalDateTime localDateTimeTo = LocalDateTime.from(dateTo.atStartOfDay());

                String key = getDateFormat(data.getTanggalDibuat());

                var isTitle = Objects.isNull(map.get(key));
                if(isTitle){
                    var dataReport = pengajuanRepository.findAllByTanggalDibuatBetween(localDateTimeFrom,localDateTimeTo)
                            .stream().map(pengajuan->mapperFacade.map(pengajuan,ReportDto.ReportPengajuanDto.class))
                            .collect(Collectors.toList());
                    total.updateAndGet(v -> v + dataReport.stream().mapToInt(j-> j.getJumlah().intValue()).sum());
                    map.put(key,dataReport);
                }
        });

        reportDto.setReportPengajuan(map);
        reportDto.setTotalPengeluaran(BigDecimal.valueOf(total.get()));
        return reportDto;
    }
}

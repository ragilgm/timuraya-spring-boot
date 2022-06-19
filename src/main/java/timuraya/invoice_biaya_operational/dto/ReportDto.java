package timuraya.invoice_biaya_operational.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 19/06/22
 **/
@Data
public class ReportDto {

    private Map<String, List<ReportPengajuanDto>> reportPengajuan;
    private BigDecimal totalPengeluaran;

    @Data
    public static class ReportPengajuanDto{
        private Long id;
        private String noPengajuan;
        private String kegiatan;
        private String keterangan;
        private String divisi;
        private BigDecimal jumlah;
        private LocalDateTime tanggalDibuat;
    }

}

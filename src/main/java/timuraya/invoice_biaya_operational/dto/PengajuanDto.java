package timuraya.invoice_biaya_operational.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import timuraya.invoice_biaya_operational.entity.HistoryPengajuan;
import timuraya.invoice_biaya_operational.entity.Pengajuan;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 08/06/22
 **/

@Data
public class PengajuanDto {

    private Long id;
    private String noPengajuan;
    private String nama;
    private String gender;
    private String kegiatan;
    private String keterangan;
    private String jumlah;
    private String divisi;
    private String tanggal;
    private String terbilang;
    private String kadiv;
    private Pengajuan.Status status;
    private LocalDateTime tanggalDibuat;
    private LocalDateTime tanggalDiupdate;
    private List<HistoryPengajuanDto> historyPengajuans;

    @Data
    public static class HistoryPengajuanDto {
        private BiodataDto biodata;
        private HistoryPengajuan.Status status;
        private String catatan;
        private String nominal;
        private LocalDateTime tanggalDibuat;
        private LocalDateTime tanggalDiupdate;

    }

    }

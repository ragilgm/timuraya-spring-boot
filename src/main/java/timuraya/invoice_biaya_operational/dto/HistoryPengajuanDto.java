package timuraya.invoice_biaya_operational.dto;

import lombok.Data;
import timuraya.invoice_biaya_operational.entity.HistoryPengajuan;
import timuraya.invoice_biaya_operational.entity.Pengajuan;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 08/06/22
 **/

@Data
public class HistoryPengajuanDto {

    private PengajuanDto pengajuan;
    private BiodataDto biodata;
    private HistoryPengajuan.Status status;
    private String catatan;

    @Data
    public static class  PengajuanDto {
        private Long id;
        private String noPengajuan;
        private String nama;
        private String gender;
        private String kegiatan;
        private String keterangan;
        private BigDecimal jumlah;
        private List<ItemDto> items;
        private String divisi;
        private String tanggal;
        private String terbilang;
        private String kadiv;
        private Pengajuan.Status status;
        private LocalDateTime tanggalDibuat;
        private LocalDateTime tanggalDiupdate;
    }

    @Data
    public static class ItemDto{
        private String nama;
        private BigDecimal harga;
    }


    @Data
    public static class BiodataDto {
        private Long id;
        private String nama;
        private String jabatan;
        private String akses;
        private String email;
        private String nohp;
        private LocalDateTime tanggalDibuat;
        private LocalDateTime tanggalDiupdate;

    }

}

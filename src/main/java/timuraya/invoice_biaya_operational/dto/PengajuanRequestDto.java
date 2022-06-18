package timuraya.invoice_biaya_operational.dto;

import lombok.Data;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 08/06/22
 **/

@Data
public class PengajuanRequestDto {
    private String kegiatan;
    private String keterangan;
    private String jumlah;
    private String divisi;
}

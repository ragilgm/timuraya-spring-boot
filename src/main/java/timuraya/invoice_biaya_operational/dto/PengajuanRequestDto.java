package timuraya.invoice_biaya_operational.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 08/06/22
 **/

@Data
public class PengajuanRequestDto {
    private String kegiatan;
    private String keterangan;
    private List<ItemRequestDto> items;
    private String jumlah;
    private String divisi;
    private String noPengajuan;

    @Data
    public static class ItemRequestDto{
        private String nama;
        private BigDecimal harga;
    }
}

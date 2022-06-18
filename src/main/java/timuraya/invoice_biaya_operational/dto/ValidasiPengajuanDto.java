package timuraya.invoice_biaya_operational.dto;

import lombok.Data;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 17/06/22
 **/

@Data
public class ValidasiPengajuanDto {
    private Long id;
    private String noPengajuan;
    private String amount;
    private String status;
}

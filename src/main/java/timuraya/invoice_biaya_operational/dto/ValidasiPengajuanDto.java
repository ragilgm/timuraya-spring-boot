package timuraya.invoice_biaya_operational.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 17/06/22
 **/

@Data
public class ValidasiPengajuanDto {
    private Long id;
    private String noPengajuan;
    private BigDecimal amount;
    private String status;
}

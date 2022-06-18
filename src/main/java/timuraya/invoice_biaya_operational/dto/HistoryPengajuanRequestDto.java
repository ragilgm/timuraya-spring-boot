package timuraya.invoice_biaya_operational.dto;

import lombok.Data;
import timuraya.invoice_biaya_operational.entity.HistoryPengajuan;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 08/06/22
 **/

@Data
public class HistoryPengajuanRequestDto {

    private Long biodataId;
    private Long pengajuanId;
    private HistoryPengajuan.Status status;
    private String catatan;

}

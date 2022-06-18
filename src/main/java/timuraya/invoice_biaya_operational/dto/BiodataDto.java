package timuraya.invoice_biaya_operational.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 05/06/22
 **/
@Data
public class BiodataDto {
    private Long id;
    private String nama;
    private String jabatan;
    private String akses;
    private String email;
    private String nohp;
    private LocalDateTime tanggalDibuat;
    private LocalDateTime tanggalDiupdate;

}

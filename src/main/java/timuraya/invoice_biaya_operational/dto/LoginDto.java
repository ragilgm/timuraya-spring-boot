package timuraya.invoice_biaya_operational.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 06/06/22
 **/

@Data
public class LoginDto {

    private Long id;
    private String username;
    private String password;
    private LocalDateTime tanggalDibuat;
    private LocalDateTime tanggalDiupdate;
    private BiodataDto biodata;

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

package timuraya.invoice_biaya_operational.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 05/06/22
 **/
@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private LocalDateTime tanggalDibuat;
    private LocalDateTime tanggalDiupdate;
}

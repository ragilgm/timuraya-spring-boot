package timuraya.invoice_biaya_operational.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 18/06/22
 **/

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Pengajuan.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "pengajuan_id", referencedColumnName = "id")
    @JsonBackReference
    private Pengajuan pengajuan;

    private String nama;
    private BigDecimal harga;
}

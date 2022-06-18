package timuraya.invoice_biaya_operational.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 05/06/22
 **/


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryPengajuan
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Pengajuan.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "pengajuan_id", referencedColumnName = "id")
    @JsonBackReference
    private Pengajuan pengajuan;

    @OneToOne(targetEntity = Biodata.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "biodata_id", referencedColumnName = "id")
    private Biodata biodata;

    @Enumerated(EnumType.STRING)
    private Status status;
    private String catatan;
    private String nominal;
    @CreationTimestamp
    private LocalDateTime tanggalDibuat;
    @UpdateTimestamp
    private LocalDateTime tanggalDiupdate;


    public enum Status{
        APPROVED,REJECT
    }
}

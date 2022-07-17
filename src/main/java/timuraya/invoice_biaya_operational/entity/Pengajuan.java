package timuraya.invoice_biaya_operational.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 05/06/22
 **/

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pengajuan {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String noPengajuan;
   private String kegiatan;
   private String keterangan;
   private BigDecimal jumlah;
   private String divisi;
   private String letterFile;
   @Builder.Default
   private boolean isValidate = false;
   @Enumerated(EnumType.STRING)
   private Status status;
   @CreationTimestamp
   private LocalDateTime tanggalDibuat;
   @UpdateTimestamp
   private LocalDateTime tanggalDiupdate;

   @OneToMany(mappedBy = "pengajuan", fetch = FetchType.LAZY)
   @JsonManagedReference
   private List<Item> items;


   @OneToMany(mappedBy = "pengajuan", fetch = FetchType.LAZY)
   @JsonManagedReference
   private List<HistoryPengajuan> historyPengajuans;

   @AllArgsConstructor
   @Getter
   public enum Status{
       SUB("ADMIN_PENGAJUAN"),
      APPROVED_BENDAHARA("ADMIN_BENDAHARA"),
//      APPROVED_KEPALA_KEUANGAN("KEPALA_KEUANGAN"),
      APPROVED("KEPALA_KEUANGAN"),
      REJECT("");

       private String approvedBy;

       public static Status getStatusByApproveBy(String approvedBy){
          return Arrays.stream(Status.values()).filter(data-> data.getApprovedBy().equals(approvedBy))
                  .findFirst()
                  .orElse(REJECT);
       }

    }

}

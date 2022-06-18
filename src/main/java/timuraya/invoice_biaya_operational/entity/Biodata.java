package timuraya.invoice_biaya_operational.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "biodata")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Biodata{

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String nama;
   private String jabatan;
   private String akses;
   private String email;
   private String nohp;
   @CreationTimestamp
   private LocalDateTime tanggalDibuat;
   @UpdateTimestamp
   private LocalDateTime tanggalDiupdate;

   @OneToOne(mappedBy = "biodata",fetch = FetchType.LAZY)
   @JsonManagedReference
   private User user;

}

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String username;
   private String password;
   @CreationTimestamp
   private LocalDateTime tanggalDibuat;
   @UpdateTimestamp
   private LocalDateTime tanggalDiupdate;


   @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
   @JoinColumn(name = "biodata_id", referencedColumnName = "id")
   @JsonBackReference
   private Biodata biodata;
}

package timuraya.invoice_biaya_operational.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import timuraya.invoice_biaya_operational.entity.Pengajuan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 05/06/22
 **/

@Repository
public interface PengajuanRepository extends JpaRepository<Pengajuan,Long>, JpaSpecificationExecutor<Pengajuan> {

    List<Pengajuan> findAllByTanggalDibuatBetween(LocalDateTime dateFrom, LocalDateTime dateTo);

}

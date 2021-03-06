package timuraya.invoice_biaya_operational.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import timuraya.invoice_biaya_operational.entity.HistoryPengajuan;
import timuraya.invoice_biaya_operational.entity.Pengajuan;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 05/06/22
 **/

@Repository
public interface PengajuanHistoryRepository extends JpaRepository<HistoryPengajuan,Long> {
}

package timuraya.invoice_biaya_operational.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import timuraya.invoice_biaya_operational.entity.Item;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 18/06/22
 **/
@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
}

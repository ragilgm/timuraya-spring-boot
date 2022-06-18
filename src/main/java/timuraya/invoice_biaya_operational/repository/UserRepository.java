
package timuraya.invoice_biaya_operational.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import timuraya.invoice_biaya_operational.entity.Biodata;
import timuraya.invoice_biaya_operational.entity.User;

import java.util.Optional;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 05/06/22
 **/

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsernameAndPassword(String username, String password);
}

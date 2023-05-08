package ps2.mack.springbootapi.contaBancaria;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Long> {

    List<ContaBancaria> findByNomeTitularContainingIgnoreCase(String nomeTitular);

}

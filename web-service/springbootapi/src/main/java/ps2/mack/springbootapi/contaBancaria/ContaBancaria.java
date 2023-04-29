package ps2.mack.springbootapi.contaBancaria;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@SpringBootApplication
@Table(name = "contas")
@Entity(name = "contas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ContaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // id, nome do titular, saldo, número da agência
    private Long id;
    private String nomeTitular;
    private double saldo;
    private String numAgencia;

    public ContaBancaria(ContaBancariaRequestDTO data) {
        this.nomeTitular = data.nomeTitular();
        this.saldo = data.saldo();
        this.numAgencia = data.numAgencia();

    }
}

package ps2.mack.springbootapi.time;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "times")
@Entity(name = "times")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Time {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer ano;
    private String cidade;
    private String estado;

    public Time(TimeRequestDTO data) {
        this.nome = data.nome();
        this.ano = data.ano();
        this.cidade = data.cidade();
        this.estado = data.estado();
    }
}

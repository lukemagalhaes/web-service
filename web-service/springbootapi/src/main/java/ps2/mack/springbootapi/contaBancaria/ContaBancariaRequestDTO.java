package ps2.mack.springbootapi.contaBancaria;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

public record ContaBancariaRequestDTO(
        @NotEmpty String nomeTitular,

        @NotNull @PositiveOrZero double saldo,

        @NotEmpty @Pattern(regexp = "\\d{5,7}") String numAgencia) {

}

package ps2.mack.springbootapi.produto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProdutoRequestDTO(
    @NotEmpty
    String descricao, 
    
    @NotEmpty
    String marca, 

    @NotNull @PositiveOrZero
    double preco) {

}

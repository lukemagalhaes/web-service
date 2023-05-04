package ps2.mack.springbootapi.time;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record TimeRequestDTO(
    @NotEmpty
    String nome, 
    
    @Min(0)
    Integer ano, 

    @NotEmpty
    String cidade, 
    
    @NotEmpty
    String estado) {

}

package ps2.mack.springbootapi.time;

public record TimeResponseDTO(Long id, String nome, Integer ano, String cidade, String estado) {
    public TimeResponseDTO(Time time){
        this(time.getId(), time.getNome(), time.getAno(), time.getEstado(), time.getCidade());
    }
}

package ps2.mack.springbootapi.produto;

public record ProdutoResponseDTO(Long id, String descricao, String marca, double preco) {
    public ProdutoResponseDTO(Produto produto) {
        this(produto.getId(), produto.getDescricao(), produto.getMarca(), produto.getPreco());
    }
}

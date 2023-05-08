package ps2.mack.springbootapi.produto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByDescricaoContainingIgnoreCase(String marcaProduto);
}
